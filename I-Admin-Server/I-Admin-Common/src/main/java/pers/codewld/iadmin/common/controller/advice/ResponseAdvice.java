package pers.codewld.iadmin.common.controller.advice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import pers.codewld.iadmin.common.model.vo.ResultVO;

import java.util.Map;

/**
 * 统一响应处理 接口增强类
 * <p>
 * 将接口返回结果加工为统一响应体
 */
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(@NotNull MethodParameter returnType, @NotNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, @NotNull MethodParameter returnType, @NotNull MediaType selectedContentType, @NotNull Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, @NotNull ServerHttpResponse response) {

        // 放行Swagger
        if (request.getURI().getPath().matches("(.*)swagger(.*)|(.*)api-docs(.*)")) {
            return body;
        }

        // 放行404响应
        if (body instanceof Map && ((Map<?, ?>) body).get("status").equals(404)) {
            return body;
        }

        // 若原返回结果为String，需要生成响应体、转换为JSON，再返回
        if (body instanceof String || String.class.equals(returnType.getGenericParameterType())) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            try {
                return mapper.writeValueAsString(ResultVO.success(body));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        // 若原返回结果已经包装为响应体，直接返回
        if (body instanceof ResultVO) {
            return body;
        }

        // 若原返回结果为Boolean值，生成成功/失败响应体
        if (body instanceof Boolean) {
            if ((Boolean) body) {
                return ResultVO.success();
            } else {
                return ResultVO.fail();
            }
        }

        return ResultVO.success(body);
    }

}
