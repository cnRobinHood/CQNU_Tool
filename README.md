# CQNU_Tool

### 接口分析

**进入研究生系统之前，需要现在校园在线登录。登录之后，密码会被cryto-js.js里面的这个方法加密。**

![image-20211015121100834](https://github.com/cnRobinHood/CQNU_Tool/blob/main/readmi_pic/image-20211015121100834.png)

**登录请求的链接为：**

https://csxrz.cqnu.edu.cn/cas/login?service=https://csxmh.cqnu.edu.cn/PersonalApplications/viewPage?active_nav_num=1

**带的参数包括验证码，加密后的密码,lt目前来看应该是个密钥**

![image-20211015125157266](https://github.com/cnRobinHood/CQNU_Tool/blob/main/readmi_pic/image-20211015125157266.png)

![image-20211015130708110](https://github.com/cnRobinHood/CQNU_Tool/blob/main/readmi_pic/image-20211015125546204.png)

**请求之后返回的Setcookie这个参数非常重要，是我们进入研究生系统的钥匙**

![image-20211015125546204](https://github.com/cnRobinHood/CQNU_Tool/blob/main/readmi_pic/image-20211015130708110.png)

**通过setcookie获得的值设置为cookie，我们可以进入**

https://yjsxt.cqnu.edu.cn/yjsxt/xtgl/index_initMenu.html?jsdm=&_t=1634274513577

**进入研究生系统之后会返回新的cookie值，格式类似于下图**

![image-20211015131206296](https://github.com/cnRobinHood/CQNU_Tool/blob/main/readmi_pic/image-20211015131206296.png)

**最后就很简单了，我们用新的cookie以及两个get请求参数(分别是xnm，xqm，前者表示学年，后者表示第几周)来构建一个get请求，我们就可以拿到相应同学的Timetable信息了。至于这个信息你要怎么用，用在Windows or ios or Android，it is your free！**



***之后的version我分析了他密码的生成原理之后，我可能会写一个Android原生 && IOS原生的app，现在用的webview，属实有点难以接受***
