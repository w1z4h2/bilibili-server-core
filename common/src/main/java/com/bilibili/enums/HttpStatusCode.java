package com.bilibili.enums;

import lombok.Getter;

@Getter
public enum HttpStatusCode {

    CONTINUE(100, "继续"),
    SWITCHING_PROTOCOLS(101, "切换协议"),
    PROCESSING(102, "处理"),
    EARLY_HINTS(103, "早期提示"),

    OK(200, "成功"),
    CREATED(201, "已创建"),
    ACCEPTED(202, "已接受"),
    NON_AUTHORITATIVE_INFORMATION(203, "非授权信息"),
    NO_CONTENT(204, "无内容"),
    RESET_CONTENT(205, "重置内容"),
    PARTIAL_CONTENT(206, "部分内容"),
    MULTI_STATUS(207, "多状态"),
    ALREADY_REPORTED(208, "已报告"),
    IM_USED(226, "IM已使用"),

    MULTIPLE_CHOICES(300, "多种选择"),
    MOVED_PERMANENTLY(301, "永久移动"),
    FOUND(302, "找到"),
    SEE_OTHER(303, "查看其他"),
    NOT_MODIFIED(304, "未修改"),
    USE_PROXY(305, "使用代理"),
    TEMPORARY_REDIRECT(307, "临时重定向"),
    PERMANENT_REDIRECT(308, "永久重定向"),

    BAD_REQUEST(400, "错误的请求"),
    UNAUTHORIZED(401, "未经授权"),
    PAYMENT_REQUIRED(402, "需要付款"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "未找到"),
    METHOD_NOT_ALLOWED(405, "不允许的方法"),
    NOT_ACCEPTABLE(406, "不可接受"),
    PROXY_AUTHENTICATION_REQUIRED(407, "需要代理身份验证"),
    REQUEST_TIMEOUT(408, "请求超时"),
    CONFLICT(409, "冲突"),
    GONE(410, "已删除"),
    LENGTH_REQUIRED(411, "需要长度"),
    PRECONDITION_FAILED(412, "前提条件失败"),
    PAYLOAD_TOO_LARGE(413, "负载过大"),
    URI_TOO_LONG(414, "URI过长"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体类型"),
    RANGE_NOT_SATISFIABLE(416, "无法满足请求范围"),
    EXPECTATION_FAILED(417, "期望失败"),
    IM_A_TEAPOT(418, "我是一个茶壶"),
    MISDIRECTED_REQUEST(421, "错误的请求"),
    UNPROCESSABLE_ENTITY(422, "无法处理的实体"),
    LOCKED(423, "被锁定"),
    FAILED_DEPENDENCY(424, "依赖失败"),
    UPGRADE_REQUIRED(426, "需要升级"),
    PRECONDITION_REQUIRED(428, "需要前提条件"),
    TOO_MANY_REQUESTS(429, "请求过多"),
    REQUEST_HEADER_FIELDS_TOO_LARGE(431, "请求头字段太大"),

    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    NOT_IMPLEMENTED(501, "未实现"),
    BAD_GATEWAY(502, "错误的网关"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    GATEWAY_TIMEOUT(504, "网关超时"),
    HTTP_VERSION_NOT_SUPPORTED(505, "不支持的HTTP版本"),
    VARIANT_ALSO_NEGOTIATES(506, "变体也协商"),
    INSUFFICIENT_STORAGE(507, "存储不足"),
    LOOP_DETECTED(508, "检测到循环"),
    NOT_EXTENDED(510, "未扩展"),
    NETWORK_AUTHENTICATION_REQUIRED(511, "需要网络身份验证");

    private final Integer code;
    private final String message;

    HttpStatusCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
