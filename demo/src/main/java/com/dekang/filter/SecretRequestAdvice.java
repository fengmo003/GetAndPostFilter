package com.dekang.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dekang.common.SecretHttpMessage;
import com.dekang.domain.Info;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * post请求对requestbody注解进行修改
 */
@ControllerAdvice
public class SecretRequestAdvice implements RequestBodyAdvice {

    //是否对请求拦截处理(根据实际情况可以写在配置文件中)
    private Boolean enable = true;

    //密钥(根据实际情况可以写在配置文件中)
    private final static String encryptKey = "abdel99999999";

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        System.out.println("【进入-RequestBody-supports】");
        //是否开启拦截
        if(!enable.equals(true)) {
            return false;
        }
        //排除get请求
//        if(methodParameter.hasMethodAnnotation(GetMapping.class)) {
//            return false;
//        }
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        System.out.println("【进入-RequestBody-beforeBodyRead】");
        System.out.println("【拦截到的请求参数为:】" + inputMessage.toString());

        String s = "";
        String requestBodyStr = getRequestBodyStr(inputMessage.getBody());
        requestBodyStr = requestBodyStr.replaceAll("\\r\\n", "");
        //解密
        JSONObject jsonObject = JSONObject.parseObject(requestBodyStr);
        //修改body数据
        String inputStr = jsonObject.getString("name");
        try {
            jsonObject.put("name","赵六");
            s= jsonObject.toJSONString();
//            s = EncrpytUtils.decryptDES(inputStr, encryptKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用解密后的数据，构造新的读取流
        InputStream rawInputStream = new ByteArrayInputStream(s.getBytes());
        return new HttpInputMessage() {
            @Override
            public HttpHeaders getHeaders() {
                return inputMessage.getHeaders();
            }

            @Override
            public InputStream getBody() throws IOException {
                return rawInputStream;
            }
        };
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        System.out.println("【进入-RequestBody-afterBodyRead】");
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        System.out.println("【进入-RequestBody-handleEmptyBody】");
        return null;
    }

    /**
     * reuqest body流数据转换为String
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String getRequestBodyStr(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        if (!ObjectUtils.isEmpty(inputStream)) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            char[] charBuffer = new char[128];
            int bytesRead = -1;
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                builder.append(charBuffer, 0, bytesRead);
            }
        }else {
            builder.append("");
        }
        return builder.toString();

    }

}
