package com.o7planning.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

// chuyển đổi đối tượng Java thành chuỗi JSON đẹp mắt bằng cách sử dụng JSON_WRITER từ thư viện Jackson
public class HelperUtils {

    public static  final ObjectWriter JSON_WRITER =  new ObjectMapper().writer().withDefaultPrettyPrinter();
}
