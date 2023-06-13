package com.swm.studywithmentor.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@PropertySource("classpath:application-${spring.profiles.active}.properties")
public class PaymentProperties {
    @Value("${app.vnp.terminalId}")
    private String vnpTmnCode;
    @Value("${app.vnp.returnURL}")
    private String vnpReturnURL;
    @Value("${app.vnp.secretKey}")
    private String vnpHashSecret;
    public static String vnpPayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static String vnpApiUrl = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";
    public static String vnpVersion = "2.1.0";
    public static String vnpCommand = "pay";
}
