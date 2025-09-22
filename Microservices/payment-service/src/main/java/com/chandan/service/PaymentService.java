package com.chandan.service;

import com.chandan.domain.PaymentMethod;
import com.chandan.model.PaymentOrder;
import com.chandan.payload.dto.BookingDTO;
import com.chandan.payload.dto.UserDTO;
import com.chandan.payload.response.PaymentLinkResponse;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

public interface PaymentService {

    PaymentLinkResponse createOrder(UserDTO user,
                                    BookingDTO booking,
                                    PaymentMethod paymentMethod) throws RazorpayException, StripeException;

    PaymentOrder getPaymentOrderById(Long id) throws Exception;
    PaymentOrder getPaymentOrderByOrderId(String paymentId);
    PaymentLink createRazorPaymentLink(UserDTO user,
                                       Long amount,
                                       Long orderId) throws RazorpayException;
    String createStripePaymentLink(UserDTO user,
                                   Long amount,
                                   Long orderId) throws StripeException;

    Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId, String paymentLinkId) throws RazorpayException;





}
