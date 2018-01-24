package com.trumpia.controller;

import com.trumpia.core.TrumpiaAPIcaller;
import com.trumpia.user.UserAccount;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*This file is part of Sign-up Page Sample.

The Sign-up Page Sample is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

The Sign-up Page Sample is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with The Sign-up Page Sample.  If not, see <http://www.gnu.org/licenses/>. 
*/


@Controller
public class RequestController implements BeanFactoryAware {

    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);
    private UserAccount user;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() throws IOException {

        return "index";
    }

	/*
     * Get form data(mobileNumber) through /sample page.
	 * and call authentication function. As response, this methods stores auth_id in HttpSession.
	 * and return to verification page
	 **/

    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public String authRequest(HttpServletRequest request, HttpSession httpSession) throws IOException {

        String mobileNumber = request.getParameter("mobileNumber");

        JSONObject authResponse = TrumpiaAPIcaller.auth(mobileNumber, user);
        if (authResponse != null) {
            String authId = authResponse.getString("auth_id");
            httpSession.setAttribute("authId", authId);
        }

        return "verification";
    }

	/*
     * Get form data(verify code) through /verification page and get auth_id through HttpSession.
	 * and call verification function.
	 * 1) if auth_id doesn't exist, return to index page(/sample). 
	 * 2) if auth_id exist, which means did send authentication function, 
	 *    call verification function.
	 * 3) if verification done successfully, move to complete page
	 * 4) if not, alert failed message and return to verification page 
	 **/

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public String verify(HttpServletRequest request, Model model, HttpSession httpSession) throws IOException {

        String verifyCode = request.getParameter("verifyCode");
        String authId = (String) httpSession.getAttribute("authId");

        //if cannot find authid in httpSession
        if (authId == null) {
            model.addAttribute("msg", "Wrong Access : Get Auth Id first");
            model.addAttribute("url", "/sample");
            return "redirect";
        }

        //verificaiton api call
        JSONObject verifyResponse = TrumpiaAPIcaller.verify(verifyCode, authId, user);

        //success
        if (verifyResponse != null && verifyResponse.has("status_code") && verifyResponse.getString("status_code").equals("MRCE0000")) {
            httpSession.removeAttribute("authId");
            model.addAttribute("msg", "Verification Successful");
            model.addAttribute("url", "/sample/complete");
            return "redirect";
        }
        //fail
        model.addAttribute("msg", "Verification failed, please check verification code.");
        model.addAttribute("url", "/sample/verification");

        return "redirect";
    }

    /*
     * return to verification page
     */
    @RequestMapping(value = "/verification",
            method = RequestMethod.GET)
    public String redirectVerify() {

        return "verification";
    }
    
    /*
     * return to complete page
     */
    @RequestMapping(value = "/complete",
            method = RequestMethod.GET)
    public String redirectComplete() {

        return "complete";
    }

    /*
     * Get  your bean that you made through user.properties, applicaitonContext.xml
     * */
    @Override
    public void setBeanFactory(BeanFactory arg0) throws BeansException {

        GenericXmlApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        user = context.getBean("userAccount", com.trumpia.user.UserAccount.class);

    }
}
