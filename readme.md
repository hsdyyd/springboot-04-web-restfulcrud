#1.导入thymeleaf
    springboot提供了thymeleaf的starter,在pom文件中加入
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    
#2.Thymeleaf的使用
    在springboot的autoconfigure的包下有对thymeleaf的自动配置
   ThymeleafAutoConfiguration中ThymeleafWebFluxConfiguration中使用了
   ThymeleafProperties来封装对Thymeleaf的自动配置
   ThymeleafProperties中public static final String DEFAULT_PREFIX = "classpath:/templates/";
    ,public static final String DEFAULT_SUFFIX = ".html";两个成员变量定义了在springboot
    中使用Thymeleaf时模板文件应该存放的位置及扩展名
    
#3.Thymeleaf的语法规则
   Thymeleaf更加简单，更加容易使用
   ##1.在使用Thymeleaf的html中导入Thmeleaf的名称空间
   ##2.基础语法  
       th:任意html属性的,改变原生属性的值
       th:insert 片段包含，等同于jsp:include
       th:replace 片段替换
       th:each 遍历 c:forEach
       th:if/th:unless/th:switch/th:case 条件判断 c:if
       th:object/th:with 声明变量 c:set
       th:attr/th:attrprepend/th:attrappend 任意属性修改支持prepend,append
       th:value/th:href/th:src... 修改指定属性的默认值
       th:text/th:utext 修改标签体内容,支持转义及不转义特殊字符
       th:fragment 声明片段
       th:remove 移除片段
        
   ##3.Simple expressions:（表达式语法）       
   ### 1).Variable Expressions: ${...}：获取变量值；OGNL；
        1）、获取对象的属性、调用方法
        2）、使用内置的基本对象：
            #ctx : the context object.
            #vars: the context variables.
               #locale : the context locale.
               #request : (only in Web Contexts) the HttpServletRequest object.
               #response : (only in Web Contexts) the HttpServletResponse object.
               #session : (only in Web Contexts) the HttpSession object.
               #servletContext : (only in Web Contexts) the ServletContext object.
               
        3）、内置的一些工具对象：
           #execInfo : information about the template being processed.
           #messages : methods for obtaining externalized messages inside variables expressions, in the same way as they would be obtained using #{…} syntax.
           #uris : methods for escaping parts of URLs/URIs
           #conversions : methods for executing the configured conversion service (if any).
           #dates : methods for java.util.Date objects: formatting, component extraction, etc.
           #calendars : analogous to #dates , but for java.util.Calendar objects.
           #numbers : methods for formatting numeric objects.
           #strings : methods for String objects: contains, startsWith, prepending/appending, etc.
           #objects : methods for objects in general.
           #bools : methods for boolean evaluation.
           #arrays : methods for arrays.
           #lists : methods for lists.
           #sets : methods for sets.
           #maps : methods for maps.
           #aggregates : methods for creating aggregates on arrays or collections.
           #ids : methods for dealing with id attributes that might be repeated (for example, as a result of an iteration).
   
   ###2).Selection Variable Expressions: *{...}：选择表达式：和${}在功能上是一样；
        补充：配合 th:object="${session.user}：
          <div th:object="${session.user}">
           <p>Name: <span th:text="*{firstName}">Sebastian</span>.</p>
           <p>Surname: <span th:text="*{lastName}">Pepper</span>.</p>
           <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p>
          </div>
       
   ###3).Message Expressions: #{...}：获取国际化内容
   ###4).Link URL Expressions: @{...}：定义URL；
              @{/order/process(execId=${execId},execType='FAST')}
   ###5).Fragment Expressions: ~{...}：片段引用表达式
              <div th:insert="~{commons :: main}">...</div>
            
        Literals（字面量）
             Text literals: 'one text' , 'Another one!' ,…
             Number literals: 0 , 34 , 3.0 , 12.3 ,…
             Boolean literals: true , false
             Null literal: null
             Literal tokens: one , sometext , main ,…
        Text operations:（文本操作）
            String concatenation: +
            Literal substitutions: |The name is ${name}|
        Arithmetic operations:（数学运算）
            Binary operators: + , - , * , / , %
            Minus sign (unary operator): -
        Boolean operations:（布尔运算）
            Binary operators: and , or
            Boolean negation (unary operator): ! , not
        Comparisons and equality:（比较运算）
            Comparators: > , < , >= , <= ( gt , lt , ge , le )
            Equality operators: == , != ( eq , ne )
        Conditional operators:条件运算（三元运算符）
            If-then: (if) ? (then)
            If-then-else: (if) ? (then) : (else)
            Default: (value) ?: (defaultvalue)
        Special tokens:
            No-Operation: _ 
 
#4.springboot的springmvc自动配置
 Spring Boot 自动配置好了SpringMVC
 
 以下是SpringBoot对SpringMVC的默认配置:**==（WebMvcAutoConfiguration）==**
 
 - Inclusion of `ContentNegotiatingViewResolver` and `BeanNameViewResolver` beans.
 
   - 自动配置了ViewResolver（视图解析器：根据方法的返回值得到视图对象（View），视图对象决定如何渲染（转发？重定向？））
   - ContentNegotiatingViewResolver：组合所有的视图解析器的；
   - ==如何定制：我们可以自己给容器中添加一个视图解析器；自动的将其组合进来；==
 
 - Support for serving static resources, including support for WebJars (see below).静态资源文件夹路径,webjars
 
 - Static `index.html` support. 静态首页访问
 
 - Custom `Favicon` support (see below).  favicon.ico
 
 - 自动注册了 of `Converter`, `GenericConverter`, `Formatter` beans.
 
   - Converter：转换器；  public String hello(User user)：类型转换使用Converter
   - `Formatter`  格式化器；  2017.12.17===Date；
 
 ```java
 		@Bean
 		@ConditionalOnProperty(prefix = "spring.mvc", name = "date-format")//在文件中配置日期格式化的规则
 		public Formatter<Date> dateFormatter() {
 			return new DateFormatter(this.mvcProperties.getDateFormat());//日期格式化组件
 		}
 ```
 
 ​	==自己添加的格式化器转换器，我们只需要放在容器中即可==
 
 - Support for `HttpMessageConverters` (see below).
 
   - HttpMessageConverter：SpringMVC用来转换Http请求和响应的；User---Json；
 
   - `HttpMessageConverters` 是从容器中确定；获取所有的HttpMessageConverter；
 
     ==自己给容器中添加HttpMessageConverter，只需要将自己的组件注册容器中（@Bean,@Component）==
 
 - Automatic registration of `MessageCodesResolver` (see below).定义错误代码生成规则
 
 - Automatic use of a `ConfigurableWebBindingInitializer` bean (see below).
 
   ==我们可以配置一个ConfigurableWebBindingInitializer来替换默认的；（添加到容器）==
 
   ```
   初始化WebDataBinder；
   请求数据=====JavaBean；
   ```
 
 **org.springframework.boot.autoconfigure.web：web的所有自动场景；**
 
 If you want to keep Spring Boot MVC features, and you just want to add additional [MVC configuration](https://docs.spring.io/spring/docs/4.3.14.RELEASE/spring-framework-reference/htmlsingle#mvc) (interceptors, formatters, view controllers etc.) you can add your own `@Configuration` class of type `WebMvcConfigurerAdapter`, but **without** `@EnableWebMvc`. If you wish to provide custom instances of `RequestMappingHandlerMapping`, `RequestMappingHandlerAdapter` or `ExceptionHandlerExceptionResolver` you can declare a `WebMvcRegistrationsAdapter` instance providing such components.
 
 If you want to take complete control of Spring MVC, you can add your own `@Configuration` annotated with `@EnableWebMvc`.
 
#5、扩展SpringMVC
 
 ```xml
     <mvc:view-controller path="/hello" view-name="success"/>
     <mvc:interceptors>
         <mvc:interceptor>
             <mvc:mapping path="/hello"/>
             <bean></bean>
         </mvc:interceptor>
     </mvc:interceptors>
 ```
 
 **==编写一个配置类（@Configuration），是WebMvcConfigurerAdapter类型；不能标注@EnableWebMvc==**;
 
 既保留了所有的自动配置，也能用我们扩展的配置；
 
 ```java
 //使用WebMvcConfigurerAdapter可以来扩展SpringMVC的功能
 @Configuration
 public class MyMvcConfig extends WebMvcConfigurerAdapter {
 
     @Override
     public void addViewControllers(ViewControllerRegistry registry) {
        // super.addViewControllers(registry);
         //浏览器发送 /atguigu 请求来到 success
         registry.addViewController("/atguigu").setViewName("success");
     }
 }
 ```
 
 原理：
 ​	1）、WebMvcAutoConfiguration是SpringMVC的自动配置类
 ​	2）、在做其他自动配置时会导入；@Import(**EnableWebMvcConfiguration**.class)
 
 ```java
     @Configuration
 	public static class EnableWebMvcConfiguration extends DelegatingWebMvcConfiguration {
       private final WebMvcConfigurerComposite configurers = new WebMvcConfigurerComposite();
 
 	 //从容器中获取所有的WebMvcConfigurer
       @Autowired(required = false)
       public void setConfigurers(List<WebMvcConfigurer> configurers) {
           if (!CollectionUtils.isEmpty(configurers)) {
               this.configurers.addWebMvcConfigurers(configurers);
             	//一个参考实现；将所有的WebMvcConfigurer相关配置都来一起调用；  
             	@Override
              // public void addViewControllers(ViewControllerRegistry registry) {
               //    for (WebMvcConfigurer delegate : this.delegates) {
                //       delegate.addViewControllers(registry);
                //   }
               }
           }
 	}
 ```
 ​	3）、容器中所有的WebMvcConfigurer都会一起起作用；
 ​	4）、我们的配置类也会被调用；
 ​	效果：SpringMVC的自动配置和我们的扩展配置都会起作用；
 
#6、全面接管SpringMVC；
 SpringBoot对SpringMVC的自动配置不需要了，所有都是我们自己配置；所有的SpringMVC的自动配置都失效了
 **我们需要在配置类中添加@EnableWebMvc即可；**
 
 ```java
 //使用WebMvcConfigurerAdapter可以来扩展SpringMVC的功能
 @EnableWebMvc
 @Configuration
 public class MyMvcConfig extends WebMvcConfigurerAdapter {
 
     @Override
     public void addViewControllers(ViewControllerRegistry registry) {
        // super.addViewControllers(registry);
         //浏览器发送 /atguigu 请求来到 success
         registry.addViewController("/atguigu").setViewName("success");
     }
 }
 ```
 
 原理：
 为什么@EnableWebMvc自动配置就失效了；
 1）@EnableWebMvc的核心
 
 ```java
 @Import(DelegatingWebMvcConfiguration.class)
 public @interface EnableWebMvc {
 ```
 
 2）、
 
 ```java
 @Configuration
 public class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport {
 ```
 
 3）、
 
 ```java
 @Configuration
 @ConditionalOnWebApplication
 @ConditionalOnClass({ Servlet.class, DispatcherServlet.class,
 		WebMvcConfigurerAdapter.class })
 //容器中没有这个组件的时候，这个自动配置类才生效
 @ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
 @AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
 @AutoConfigureAfter({ DispatcherServletAutoConfiguration.class,
 		ValidationAutoConfiguration.class })
 public class WebMvcAutoConfiguration {
 ```
 
 4）、@EnableWebMvc将WebMvcConfigurationSupport组件导入进来；
 
 5）、导入的WebMvcConfigurationSupport只是SpringMVC最基本的功能；
 
 
#7、如何修改SpringBoot的默认配置
 模式：
 ​	1）、SpringBoot在自动配置很多组件的时候，先看容器中有没有用户自己配置的（@Bean、@Component）如果有就用用户配置的，如果没有，才自动配置；如果有些组件可以有多个（ViewResolver）将用户配置的和自己默认的组合起来；
 ​	2）、在SpringBoot中会有非常多的xxxConfigurer帮助我们进行扩展配置
 ​	3）、在SpringBoot中会有很多的xxxCustomizer帮助我们进行定制配置            
        