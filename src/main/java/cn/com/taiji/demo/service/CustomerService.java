package cn.com.taiji.demo.service;

import cn.com.taiji.demo.bean.Award;
import cn.com.taiji.demo.bean.User;
import cn.com.taiji.demo.controller.LotteryController;
import cn.com.taiji.demo.util.ExcelUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Service
public class CustomerService {

    private static Logger log = LoggerFactory.getLogger(LotteryController.class);
    @Value("${upload.dir.lottery}")
    private String uploadDir;

    @Value("${lottery.doneStatus}")
    private String doneStatus;

    //抽奖执行后的用户集
    private static List<User> awardUserList = Collections.EMPTY_LIST;

    private static List<User> userList = Collections.EMPTY_LIST;

    private static List<Award> awardList = Collections.EMPTY_LIST;

    private static final ObjectMapper mapper = new ObjectMapper();
    //批量导入客户
    public Map batchImport(String name, MultipartFile file, String fileType) {
        Map result = new HashMap<String, Object>();
        String flg = "N";
        String msg = "";
        //判断文件是否为空
        if (file == null) {
            msg += "请选择要上传的模板文件";
        } else {
            //获取文件名
            String fileName = file.getOriginalFilename();
            //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
            long size = file.getSize();
            if (fileName == null || ("").equals(fileName) && size == 0) {
                msg += "文件大小或名称不能为空";
                result.put("flg", flg);
                result.put("msg", msg);
                return result;
            }
            if (fileName == null || !(ExcelUtil.isExcel2003(fileName) || ExcelUtil.isExcel2007(fileName))) {
                msg += "文件名不是excel格式";
                result.put("flg", flg);
                result.put("msg", msg);
                return result;
            }
        }
        File dir = new File(uploadDir);
        //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
        if (!dir.exists()) dir.mkdirs();
        //新建一个文件
        File newFile = new File(uploadDir + File.separator + new Date().getTime() + "_" + name);
        //将上传的文件写入新建的文件中
        try {
            file.transferTo(newFile);
        } catch (Exception e) {
            log.error("写入excel文件失败", e);
            result.put("flg", flg);
            result.put("msg", "写入excel文件失败:" + e.getMessage());
            return result;
        }


        //初始化输入流
        InputStream in;
        if ("user".equals(fileType)) { //读取excel表数据
            log.error("初始化客户信息的集合");
            try {
                in = new FileInputStream(newFile);
                userList = convertExcelData(in, "user");
            } catch (FileNotFoundException e) {
                log.error("读取excel输入流错误", e);
            }


        } else if ("number".equals(fileType)) {
            log.error("初始化号码信息的集合");
            try {
                in = new FileInputStream(newFile);
                awardList = convertExcelData(in, "number");
            } catch (FileNotFoundException e) {
                log.error("读取excel输入流错误", e);
            }
        }

        result.put("flg", "Y");
        result.put("msg", "导入成功");
        log.error("打印内存中导入的集合");
        printDetailData();
        return result;
    }

    private List convertExcelData(InputStream in, String type) {
        Workbook wb;
        List<User> users = new ArrayList<>();
        List<Award> awards = new ArrayList<>();
        try {
            int totalRows = 0;
            int totalCells = 0;
            wb = new XSSFWorkbook(in);
            //得到第一个shell
            Sheet sheet = wb.getSheetAt(0);
            //得到Excel的行数
            totalRows = sheet.getPhysicalNumberOfRows();
            //得到Excel的列数(前提是有行数)
            if (totalRows >= 1 && sheet.getRow(0) != null) {
                totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
            }
            User user;
            Award award;
            //循环Excel行数,从第二行开始。标题不入库
            if ("user".equals(type)) {
                for (int r = 1; r < totalRows; r++) {
                    Row row = sheet.getRow(r);
                    if (row == null || "".equals(row.getCell(0).getStringCellValue())) continue;

                    user = new User();
                    for (int c = 0; c < totalCells; c++) { //循环Excel的列
                        Cell cell = row.getCell(c);
                        if (null != cell) {
                            if (c == 0) {
                                user.setUserName(StringUtils.trim(cell.getStringCellValue()));
                            }  else if (c == 1) {
                                user.setDeptName(StringUtils.trim(cell.getStringCellValue()));
                            } else if (c == 2) {
                                if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
                                    if(cell.getNumericCellValue()>0){
                                        user.setPrePickFlag("Y");
                                        user.setPhoneNumber(Double.toString(cell.getNumericCellValue()));
                                    }
                                }
                                if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING){
                                    if(StringUtils.isNotBlank(cell.getStringCellValue())){
                                        user.setPrePickFlag("Y");
                                        user.setPhoneNumber(StringUtils.trim(cell.getStringCellValue()));
                                    }
                                }
                            }
                        }
                        user.setIndex(String.valueOf(r));
                    }
                    users.add(user);
                }
                return users;
            } else if ("number".equals(type)) {
                for (int r = 1; r < totalRows; r++) {
                    Row row = sheet.getRow(r);
                    award = new Award();
                    for (int c = 0; c < totalCells; c++) { //循环Excel的列
                        Cell cell = row.getCell(c);
                        if (null != cell) {
                            if (c == 0) {
                                    String str="";
                                if(cell.getCellType()== HSSFCell.CELL_TYPE_NUMERIC) {
                                    str=String.valueOf(cell.getNumericCellValue());//号码
                                }else{
                                   str=String.valueOf(cell.getStringCellValue());//号码
                                }
                                if(StringUtils.isBlank(str)) continue;
                                award.setName(str);
                            }
//                            else if (c == 1) {
//                                award.setFrozenFlag(cell.getStringCellValue());//绑定标志
//                            } else if (c == 2) {
//                                award.setCode(cell.getStringCellValue());//绑定人员编号
//                            }
                        }
                    }
                    awards.add(award);
                }
                return awards;
            }

        } catch (IOException e) {
            log.error("创建workbook时出现错误", e);
        }
        return Collections.EMPTY_LIST;
    }

    public static List<User> getAwardUserList() {
        if (awardUserList.isEmpty()) awardUserList = userList;
        return awardUserList;
    }

    public static void setAwardUserList(List<User> awardUserList) {
        CustomerService.awardUserList = awardUserList;
    }

    public static List<User> getUserList() {
        return userList;
    }

    public static void setUserList(List<User> userList) {
        CustomerService.userList = userList;
    }

    public static List<Award> getAwardList() {
        return awardList;
    }

    public static void setAwardList(List<Award> awardList) {
        CustomerService.awardList = awardList;
    }

    public String getDoneStatus() {
        return doneStatus;
    }

    public void setDoneStatus(String doneStatus) {
        this.doneStatus = doneStatus;
    }

    public void printDetailData() {
        log.debug("打印参与抽奖的人员清单：");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < userList.size(); i++) {
            User u = userList.get(i);
            sb.append((i + 1) + "|" + u.getUserName() + "|" + u.getUserNumber() + "|" + u.getWorkUnitName() + "|" + u.getDeptName() + "\n");
        }
        log.error("\n" + sb.toString());
        log.error("奖品清单：");
        sb.delete(0, sb.length());
        for (int i = 0; i < awardList.size(); i++) {
            Award u = awardList.get(i);
            sb.append((i + 1) + "|" + u.getAttr1() + "|" + u.getAttr2() + "|" + u.getAttr3() + "\n");
        }
        log.error("\n" + sb.toString());

    }

    public void printAwardResult(){
        log.debug("打印选号结果清单：");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < awardUserList.size(); i++) {
            User u = awardUserList.get(i);
            sb.append((i + 1) + "|" + u.getUserName() + "|" + u.getUserNumber() + "|" + u.getWorkUnitName() + "|" + u.getDeptName() +"|"+u.getPhoneNumber()+ "\n");
        }
        log.error("\n" + sb.toString());
    }

    public synchronized List<User> goRandom() {

        log.error("准备执行随机分配号码");
        if ("N".equals(doneStatus)) {
            log.error("正在处理已被占用的号码并关联相应用户用户");
            for (Award a : awardList) {
                if ("Y".equals(a.getFrozenFlag())) {
                    for (User u : userList) {
                        if (u.getUserNumber().equals(a.getCode())) {
                            log.error("号码：{}，关联用户：{},{}", a.getName(), u.getUserName(), u.getDeptName());
                            u.setPrePickFlag("Y");
                            u.setAward(a);
                            break;
                        }

                    }
                }
            }
            log.error("正在执行随机分配号码");
            for (User u : userList) {
                if ("Y".equals(u.getPrePickFlag())) {
                    //u.setPhoneNumber("已有");
                    log.error("用户：{} {}，不参与选号正在执行随机分配号码", u.getUserName(), u.getDeptName());
                    continue;
                }
                if (StringUtils.isNotBlank(u.getPhoneNumber())) {
                    log.error("用户：{} {}，手机字段居然有分配号码：{}", u.getUserName(), u.getDeptName(), u.getPhoneNumber());
                    continue;
                }
                Award a = pickRandomAward();
                u.setPhoneNumber(a.getName());
                u.setAward(a);
                log.error("用户：{} {}，从号码集合中随机分配号码{}", u.getUserName(), u.getDeptName(), a.getName());


            }
            awardUserList = userList;
            if(!awardUserList.isEmpty()) doneStatus = "Y";
        }
        printAwardResult();
        saveJsonObject();
        return awardUserList;
    }

    private Award pickRandomAward() {
        Award award = null;
        log.error("随机选一个号码开始");
        List<Award> picked = new ArrayList<>();
        List<Award> unPicked = new ArrayList<>();

        for (Award a : awardList) {
            if ("Y".equals(a.getFrozenFlag())) {
                picked.add(a);
            } else {
                unPicked.add(a);
            }
        }
        if (!unPicked.isEmpty()) {
            //集合洗牌
            Collections.shuffle(unPicked);
            //从新取一个元素出来给用户
            award = unPicked.get(0);
            award.setFrozenFlag("Y");
            log.error("抽取到的号码为：{}", award.getName());
        }
        return award;
    }

    public synchronized User nextPickNumber(User user){

        if(user.getPickTimes()<3){
            log.error("用户：{} {}，第{}次获取随机号码",user.getUserName(), user.getDeptName(),user.getPickTimes()+1);
            user.getPickList().clear();
            int i=1;
            Collections.shuffle(awardList);
            for (Award a:awardList
                 ) {
                if(i>3) break;
                if("Y".equals(a.getFrozenFlag())) continue;
                if("Y".equals(a.getPickFlag())) continue;
                if("Y".equals(a.getPickFrozen())) continue;
                log.error("用户：{} {}，获得随机号码：{}", user.getUserName(),user.getDeptName(),a.getName());
                a.setPickFrozen("Y");
                user.getPickList().add(a);
                if(i==3){
                    user.setLastRamdomNumber(a.getName());
                }
                i++;
            }
            user.setPickTimes(user.getPickTimes()+1);
        }
        return user;
    }

    public void saveJsonObject(){
       log.error("主要对象json数据：");
       log.error("user：{}",toJson(CustomerService.getUserList()));
       log.error("award：{}",toJson(CustomerService.getAwardList()));
       log.error("awardUser：{}",toJson(CustomerService.getAwardUserList()));
    }
    /**
     * 将对象转化为json
     * @author yangwenkui
     * @time 2017年3月16日 下午2:55:10
     * @param obj 待转化的对象
     * @return 当转化发生异常时返回null
     */
    public static String toJson(Object obj){
        if(obj == null){
            return null;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            log.error(String.format("obj=[%s]", obj.toString()), e);
        }
        return null;
    }

}
