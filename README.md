# GetAndPostFilter
对get请求和post请求进行解密处理。
其中，ParameterRequestWrapper类用于重新request对象，作用为修改get请求参数的存储。
SecretHttpMessage类用于重新输入流对象，作用为修改post请求参数的存储。
如果业务逻辑进行解密或者拦截处理，QueryInterceptor（修改header和parameter参数集合）和SecretRequestAdvice（修改requestBody）。
业务流程：前端--》发送请求--》filter拦截器--》ControllerAdvice--》controller控制层--》service层代码流程
postman文件夹存储的是测试demo
