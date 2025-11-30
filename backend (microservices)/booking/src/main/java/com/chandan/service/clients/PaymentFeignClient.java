package com.chandan.service.clients;

import com.razorpay.RazorpayException;
import com.chandan.domain.PaymentMethod;
import com.chandan.exception.UserException;
import com.chandan.modal.Booking;
import com.chandan.payload.response.PaymentLinkResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("payment")
public interface PaymentFeignClient {

    @PostMapping("/api/payments/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @RequestHeader("Authorization") String jwt,
            @RequestBody Booking booking,
            @RequestParam PaymentMethod paymentMethod) throws UserException,
            RazorpayException;
}
