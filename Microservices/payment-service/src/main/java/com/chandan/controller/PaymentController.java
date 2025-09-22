package com.chandan.controller;

import com.chandan.domain.PaymentMethod;
import com.chandan.model.PaymentOrder;
import com.chandan.payload.dto.BookingDTO;
import com.chandan.payload.dto.UserDTO;
import com.chandan.payload.response.PaymentLinkResponse;
import com.chandan.service.PaymentService;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @RequestBody BookingDTO booking,
            @RequestParam PaymentMethod paymentMethod
            ) throws StripeException, RazorpayException {

        UserDTO user=new UserDTO();
        user.setFullName("Chandan");
        user.setEmail("chandan@gmail.com");
        user.setId(1L);

        PaymentLinkResponse res=paymentService.createOrder(user,booking,paymentMethod);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<PaymentOrder> getPaymentOrderById(
            @PathVariable Long paymentOrderId
    ) throws Exception {



        PaymentOrder res=paymentService.getPaymentOrderById(paymentOrderId);
        return ResponseEntity.ok(res);
    }

    @PatchMapping("/{paymentOrderId}")
    public ResponseEntity<Boolean> proceedPayment(
            @RequestParam String paymentId,
            @RequestParam String paymentLinkId
    ) throws Exception {
        PaymentOrder paymentOrder=paymentService.getPaymentOrderByOrderId(paymentLinkId);

        Boolean res=paymentService.proceedPayment(paymentOrder,paymentId,paymentLinkId);
        return ResponseEntity.ok(res);
    }



}
