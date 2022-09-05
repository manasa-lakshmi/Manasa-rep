<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<meta http-equiv="X-UA-Compatible" content="IE=EDGE"/>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
        <meta http-equiv="X-UA-Compatible" content="IE=EDGE"/>
        <head>
                <title id="pageTitle">Sign In</title>
                <link id="loginTemplateArabicCSS" rel="stylesheet"
                      type="text/css"
                      href="css/loginTemplate_rtl.css"/>
                <link id="loginTemplateCSS" rel="stylesheet" type="text/css"
                      href="<%=request.getContextPath()%>/css/loginTemplate.css"/>
                <meta http-equiv="Content-Type"
                      content="text/html; charset=UTF-8"/>
                <!-- Start Disable frame hijacking Script-->
        </head>
        <body>
                <div class="header">
                        <!-- start Branding Area -->
                        <div class="container">
                                <div class="brandContainer">
                                        <div class="imageContainer">
                                                <img alt="ORACLE CLOUD"
                                                     src="<%=request.getContextPath()%>/images/mcs_login_324.png"
                                                     width="325" height="325"></img>
                                        </div>
                                        <div class="branding">
                                                <div class="head1"
                                                     id="loginTitle">Sign In</div>
                                                <div class="head2">Oracle
                                                                   Applications
                                                                   Cloud</div>
                                        </div>
                                </div>
                        </div>
                        <div class="stripe"></div>
                        <!-- end Branding Area -->
                </div>
                <div class="content">
                        <div class="copyrightFooter">
                                <div class="foot">
                                        <div class="Ologo">
                                                <img alt="ORACLE ®"
                                                     src="<%=request.getContextPath()%>/images/OracleLogo.png"
                                                     width="139" height="42"/>
                                        </div>
                                </div>
                                <div class="copyright" id="copyright">Copyright(C)
                                                                      2011,
                                                                      2016,
                                                                      Oracle
                                                                      and/or its
                                                                      affiliates.
                                                                      All rights
                                                                      reserved.</div>
                        </div>
                        <!-- end footer -->
                        <div class="message-row">
                                <noscript>
                                        <p class="loginFailed">JavaScript is
                                                               required. Enable
                                                               JavaScript to use
                                                               WebLogic
                                                               Administration
                                                               Console.</p>
                                </noscript>
                        </div>
                        
                        <div class="contentContainer">
                              
                                <div class="formContainer">
                                
                                        <form name="loginForm"
                                              class="signin_form" id="Login"
                                              action="<%=request.getContextPath()%>/j_security_check"
                                              method="post">
                                        
                                       <c:choose>
    <c:when test="${param.failed!=null}">
          <div class="error" id="error">
                                        Invalid Username or Password</div>
    </c:when>
  
    <c:when test="${param.unauthroized!=null}">
          <div class="error" id="error">
                                        You are not authorized to view this page.</div>
    </c:when>
  
  
  
</c:choose>
                                     
                                        
                                        <label name="useridLabel"
                                               id="useridLabel"
                                               style="display:none;">User
                                                                             ID</label>
                                         
                                        <input name="j_username" id="userid"
                                               class="textInput" type="text"
                                               placeholder="User ID"/><br/>
                                         
                                        <label name="passwordLabel"
                                               id="passwordLabel"
                                               style="display:none;">Password</label>
                                         
                                        <input name="j_password" id="password"
                                               class="textInput" type="password"
                                               placeholder="Password"/><br/>
                                         
                                     
                                         
                                        <button type="submit" name="btnActive"
                                                value="Submit">  Sign In </button>
                                         
                                        <br/><br/>
                                         
                                </div>
                        </div>
                </div>
        </body>
</html>