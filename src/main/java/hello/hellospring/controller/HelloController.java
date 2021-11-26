package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HelloController {


    @GetMapping("hello") //http get방식
    private String hello(Model model) //model을 이용해 데이터를 전달
    {
        model.addAttribute("data","hello dolldori!!");
        return "hello";  // hello 이름을 가진 화면을 실행 resources>template>hello.html
                         //리턴값으로 문자를 반환하면 viewResolver가 화면을 찾아서 자동처리(기본적으로 viewName을 매핑)
    }


    @GetMapping("hello-mvc")
    //파라미터 받을때 required 디폴트옵션 true>false로 변경시켜주면 없어도 오류X
    private String helloMvc(@RequestParam(value="name", required = false) String name, Model model)
    {
        model.addAttribute("name", name);
        return "hello-template";  //viewResolver가 찾아서 데이터를 넘겨 Thymeleaf 템플릿엔진을 통해 변환되어 화면그림
    }


    @GetMapping("hello-string")
    @ResponseBody    //HTTP 응답 body에 데이터(string, 객체)를 직접 넣어주겠다.
    private String helloString(@RequestParam("name") String name)
    {
        return "hello " +name;  //화면에서 소스보기(html태그X, 템플릿 없이 문자그대로)
    }



    @GetMapping("hello-api")
    @ResponseBody
    private Hello helloApi(@RequestParam("name") String name) //리턴값이 문자가아닌 객체이용
    {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;  //json형식그대로전달 {"name":"spring"}
    }
    //기존에는 viewResolver가 동작했다면, @ResponseBody의 경우 HttpMessageConverter가 동작
    //HttpMessageConverter(문자면 StringConverter 객체면 JsonConverter로 변경하여)가 전달
    //객체를 json으로 바꿔주는 라이브러리(Jackson, Json...)이용하여 JSON형식으로 전달:)



    static class Hello{
        private String name;

        //프로퍼티접근방식- getter,setter
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }




};