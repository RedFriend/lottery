package cn.com.taiji.demo.controller;

import cn.com.taiji.demo.bean.Award;
import cn.com.taiji.demo.bean.User;
import cn.com.taiji.demo.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;


@RestController
public class SystemController {

    private static Logger log = LoggerFactory.getLogger(SystemController.class);
    @Autowired
    private CustomerService customerService;

    /**
     * 登录页面
     *
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login() {
        log.error("进入页面login");
        ModelAndView page = new ModelAndView("/login");
        page.addObject("doneStatus", customerService.getDoneStatus());
        Map deptMap = new HashMap();
        for (User u : CustomerService.getAwardUserList()) {
            deptMap.put(u.getDeptName(), u.getDeptName());
        }
        page.addObject("doneStatus",customerService.getDoneStatus());
        page.addObject("deptMap", deptMap);
        return page;
    }

    /**
     * 登录
     *
     * @return
     */
    @RequestMapping("/goLogin")
    public ModelAndView goLogin(String deptName, String userName, String password) {
        log.error("进行登录验证");
        boolean flg=false;
        User user=null;
        for (User u : CustomerService.getAwardUserList()) {
            if (deptName.equals(u.getDeptName()) && userName.trim().equals(u.getUserName()) && password.length()>=4&&password.trim().contains(password)) {
                flg=true;
                user=u;
                break;
            }
        }
        ModelAndView page;
        if(flg){
            page = new ModelAndView("/pick");
            int count=0;
            for (Award a:CustomerService.getAwardList()
                    ) {
                if("Y".equals(a.getFrozenFlag())||"Y".equals(a.getPickFlag())||"Y".equals(a.getPickFrozen())){
                    count++;
                }
            }
            page.addObject("usableTotal",CustomerService.getAwardList().size()-count);
//            if(user.getPickTimes()==0){
//                user=customerService.nextPickNumber(user);
//            }
            page.addObject("user",user);
            return page;
        }else{
            page=new ModelAndView("redirect:/login");
            page.addObject("flg","N");
            page.addObject("doneStatus",customerService.getDoneStatus());
            page.addObject("msg","登录名失败，请输入正确的部门、用户名和密码");
        }

        return page;
    }
}
