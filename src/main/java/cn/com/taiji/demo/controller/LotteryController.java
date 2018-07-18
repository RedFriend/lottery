package cn.com.taiji.demo.controller;

import cn.com.taiji.demo.bean.Award;
import cn.com.taiji.demo.bean.User;
import cn.com.taiji.demo.service.CustomerService;
import cn.com.taiji.demo.util.PageInfo;
import cn.com.taiji.demo.util.WriteExcel;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.*;


@RestController
@RequestMapping("/lottery")
public class LotteryController {

    private static Logger log = LoggerFactory.getLogger(LotteryController.class);
    @Autowired
    private CustomerService customerService;

    @RequestMapping("/index")
    public ModelAndView lottery() {
        log.error("进入lottery页面");
        ModelAndView page = new ModelAndView("/lottery");
        Collections.sort(CustomerService.getAwardUserList(), Comparator.comparing(User::getIndexNo));
        page.addObject("list", CustomerService.getAwardUserList());
        page.addObject("award", CustomerService.getAwardList());
        page.addObject("doneStatus", customerService.getDoneStatus());
        return page;
    }

    /**
     * 打印页面
     *
     * @return
     */
    @RequestMapping("/print")
    public ModelAndView print(String deptName) {
        log.error("进入print页面");
        List<User> list = new ArrayList();
        ModelAndView page = new ModelAndView("/print");
        if (StringUtils.isNotBlank(deptName) && !"ALL".equals(deptName)) {

            for (User u : CustomerService.getAwardUserList()) {
                if (u.getDeptName().equals(deptName)) list.add(u);
            }
        } else {
            list = CustomerService.getAwardUserList();
        }
        Collections.sort(list, Comparator.comparing(User::getDeptName));
        page.addObject("list", list);
        Map selectMap = new HashMap();
        for (User u : CustomerService.getAwardUserList()) {
            selectMap.put(u.getDeptName(), u.getDeptName());
        }
        page.addObject("selectMap", selectMap);
        page.addObject("doneStatus", customerService.getDoneStatus());
        return page;
    }

    /**
     * 打印页面
     *
     * @return
     */
    @RequestMapping("/printDept")
    public ModelAndView printDept(String deptName) {
        log.error("进入print页面");
        List<User> list = new ArrayList();
        ModelAndView page = new ModelAndView("/print");
        if (StringUtils.isNotBlank(deptName) && !"ALL".equals(deptName)) {

            for (User u : CustomerService.getAwardUserList()) {
                if (u.getDeptName().equals(deptName)) list.add(u);
            }
        } else {
            list = CustomerService.getAwardUserList();
        }
        Collections.sort(list, Comparator.comparing(User::getDeptName));
        page.addObject("list", list);
        Map selectMap = new HashMap();
        for (User u : CustomerService.getAwardUserList()) {
            selectMap.put(u.getDeptName(), u.getDeptName());
        }

        Map<String, List> printDeptMap = new HashMap<>();
        for (User u : CustomerService.getAwardUserList()) {
            if (printDeptMap.containsKey(u.getDeptName())) {
                List deptList = printDeptMap.get(u.getDeptName());
                deptList.add(u);
            } else {
                List deptList = new ArrayList();
                deptList.add(u);
                printDeptMap.put("printDeptMap", deptList);
            }
        }


        page.addObject("printDeptMap", printDeptMap);
        page.addObject("selectMap", selectMap);
        page.addObject("doneStatus", customerService.getDoneStatus());
        return page;
    }

    /**
     * 打印页面
     *
     * @return
     */
    @RequestMapping("/pickPrint")
    public ModelAndView pickPrint(String deptName) {
        log.error("进入print页面");
        List<User> list = new ArrayList();
        ModelAndView page = new ModelAndView("/pickPrint");
        if (StringUtils.isNotBlank(deptName) && !"ALL".equals(deptName)) {

            for (User u : CustomerService.getAwardUserList()) {
                if (u.getDeptName().equals(deptName)) list.add(u);
            }
        } else {
            list = CustomerService.getAwardUserList();
        }
        Collections.sort(list, Comparator.comparing(User::getDeptName));
        page.addObject("list", list);
        Map selectMap = new HashMap();
        for (User u : CustomerService.getAwardUserList()) {
            selectMap.put(u.getDeptName(), u.getDeptName());
        }
        page.addObject("selectMap", selectMap);
        page.addObject("doneStatus", customerService.getDoneStatus());
        return page;
    }

    @RequestMapping("/disorder")
    public ModelAndView disorder() {
        log.error("执行洗牌操作");
        ModelAndView page = new ModelAndView("/lottery");
        Collections.shuffle(CustomerService.getAwardUserList());
        page.addObject("list", CustomerService.getAwardUserList());
        page.addObject("award", CustomerService.getAwardList());
        page.addObject("doneStatus", customerService.getDoneStatus());
        return page;
    }

    @RequestMapping("/setting")
    public ModelAndView setting() {
        log.error("进入setting页面");
        ModelAndView page = new ModelAndView("/setting");

        System.getProperties();
        return page;
    }

    @PostMapping("/setting/importUsers")
    @ResponseBody
    public Map importUsers(@RequestParam(value = "userFile") MultipartFile file,
                           HttpServletRequest request, HttpServletResponse response) {

        log.error("导入用户数据");
        //获取文件名
        String fileName = file.getOriginalFilename();
        Map result = customerService.batchImport(fileName, file, "user");
        return result;
    }

    @GetMapping("/setting/reset")
    @ResponseBody
    public Map reset() {
        Map result = new HashMap<String, Object>();
        String flg = "Y";
        String msg = "重置成功";
        log.error("重置数据前打印内存中导入的集合");
        customerService.printAwardResult();
        log.error("重置数据");
        CustomerService.setAwardUserList(Collections.EMPTY_LIST);
        CustomerService.setUserList(Collections.EMPTY_LIST);
        CustomerService.setAwardList(Collections.EMPTY_LIST);
        customerService.setDoneStatus("N");

        result.put("flg", flg);
        result.put("msg", msg);
        return result;
    }

    @PostMapping("/setting/importNumbers")
    @ResponseBody
    public Map importNumbers(@RequestParam(value = "numberFile") MultipartFile file,
                             HttpServletRequest request, HttpServletResponse response) {

        log.error("导入号码数据");
        //获取文件名
        String fileName = file.getOriginalFilename();
        Map result = customerService.batchImport(fileName, file, "number");
        return result;
    }

    @GetMapping("/setting/users")
    @ResponseBody
    public PageInfo getUsers(User user) {

        PageInfo<User> pageInfo = new PageInfo<User>();
        List list = new ArrayList<User>();
        User testUser = new User();
        testUser.setUserName("zhangsan");
        testUser.setUserNumber("123456");
        list.add(testUser);
        pageInfo.setRows(list);
        pageInfo.setTotal(1);
        pageInfo.setPage(1);
        pageInfo.setRecords(7);
        return pageInfo;
    }

    @GetMapping("/setting/downloadTemplate")
    public void downloadExcelTemplate(String templateType, HttpServletRequest request, HttpServletResponse response) {

        try {
            String fileName;
            if ("user".equals(templateType)) {
                fileName = "人员信息模板.xlsx";
            } else if ("number".equals(templateType)) {
                fileName = "号码信息模板.xlsx";
            } else {
                return;
            }
            Resource resource = new ClassPathResource("static/excel/" + fileName);
            BufferedInputStream in = new BufferedInputStream(resource.getInputStream());
            OutputStream out = response.getOutputStream();
            response.reset();
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

            int len;
            byte[] buff = new byte[10240];
            while ((len = in.read(buff)) != -1) {
                out.write(buff, 0, len);
            }
            out.close();
            in.close();
        } catch (IOException e) {
            log.error("读取模板文件错误", e);
            response.setContentType("text/html; charset=UTF-8"); //转码
            PrintWriter out = null;
            try {
                out = response.getWriter();
            } catch (IOException e1) {
                log.error("响应错误");
            }
            out.flush();
            out.println("<script>");
            out.println("alert('下载文件不存在！');");
            out.println("window.close();");
            out.println("</script>");
            return;
        }
    }

    @GetMapping("/setting/downLeftNumberExcel")
    public void downLeftNumberExcel(HttpServletRequest request, HttpServletResponse response) {

        try {
            List<Award> leftNumbers = new ArrayList<>();
            //查出剩余号码
            for (Award award : CustomerService.getAwardList()) {
                boolean isNotPicked = true;
                for (User user : CustomerService.getAwardUserList()) {
                    if (award.getName().equals(user.getPickNumber())||
                            award.getName().equals(user.getPhoneNumber())
                ||award.getName().equals(user.getLastRamdomNumber())) {
                        isNotPicked = false;
                    }
                }
                if (isNotPicked) {
                    leftNumbers.add(award);
                }
            }

            OutputStream out = response.getOutputStream();
            response.reset();
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("剩余号码.xls", "UTF-8"));
            Map<String, String> head = new HashMap<>();
            head.put("name", "剩余号码");
            log.error("导出剩余号码{}",JSON.toJSONString(leftNumbers));
            WriteExcel.exportExcel2007NotTitle(null, head, JSON.parseArray(JSON.toJSONString(leftNumbers)), null, out);
            out.close();
        } catch (IOException e) {
            log.error("读取模板文件错误", e);
            response.setContentType("text/html; charset=UTF-8"); //转码
            PrintWriter out = null;
            try {
                out = response.getWriter();
            } catch (IOException e1) {
                log.error("响应错误!");
            }
            out.flush();
            out.println("<script>");
            out.println("alert('下载文件不存在！');");
            out.println("window.close();");
            out.println("</script>");
            return;
        }
    }

    @GetMapping("/goRandom")
    @ResponseBody
    public List goRandom(User user) {
        return customerService.goRandom();
    }

    @PostMapping("/pickRandom")
    @ResponseBody
    public ModelAndView pickRandom(User user) {
        log.error("用户：{} {}，进行重新选号", user.getUserName(), user.getDeptName());
        boolean flg = false;
        for (User u : CustomerService.getUserList()
                ) {
            if (user.getDeptName().equals(u.getDeptName()) && user.getUserName().equals(u.getUserName())) {
                user = u;
                flg = true;
                break;
            }
        }
        ModelAndView page = new ModelAndView("/pick");
        if (!flg) {
            page.addObject("flg", "N");
            page.addObject("msg", "用户错误");
            return page;
        }
        User userPicked = customerService.nextPickNumber(user);
        page.addObject("flg", "Y");
        page.addObject("doneStatus", customerService.getDoneStatus());
        page.addObject("msg", "号码分发成功");
        page.addObject("user", userPicked);
        int count = 0;
        for (Award a : CustomerService.getAwardList()
                ) {
            if ("Y".equals(a.getFrozenFlag()) || "Y".equals(a.getPickFlag()) || "Y".equals(a.getPickFrozen())) {
                count++;
            }
        }
        page.addObject("usableTotal", CustomerService.getAwardList().size() - count);
        return page;
    }

    @PostMapping("/certainPick")
    @ResponseBody
    public Map certainPick(User user) {
        Map result = new HashMap();
        result.put("flg", "N");
        result.put("msg", "操作失败，您不能再选定了");
        boolean flg = false;
        for (User u : CustomerService.getUserList()) {
            if (user.getDeptName().equals(u.getDeptName()) && user.getUserName().equals(u.getUserName())) {
                if (StringUtils.isNotBlank(u.getPickNumber())) {
                    break;
                }
                for (Award a : u.getPickList()) {
                    if (a.getName().equals(user.getPickNumber())) {
                        a.setPickFlag("Y");
                        a.setPickFrozen("Y");
                        u.setPickNumber(a.getName());
                        break;
                    }

                }
                log.error("用户：{} {}，选择了{}", user.getUserName(), user.getDeptName(), u.getPickNumber());
                user = u;
                flg = true;
                break;
            }
        }

        if (flg) {
            result.put("flg", "Y");
            result.put("msg", "选定成功");
            result.put("user", user);
        }
        return result;
    }

    @PostMapping("/deptPerson")
    @ResponseBody
    public List deptPerson(User user) {
        List<User> list = new ArrayList<>();
        for (User u : CustomerService.getUserList()) {
            if (user.getDeptName().equals(u.getDeptName())) {
                list.add(u);
            }
        }
        return list;
    }
}
