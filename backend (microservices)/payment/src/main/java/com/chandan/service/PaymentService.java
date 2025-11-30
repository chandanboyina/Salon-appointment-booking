package com.chandan.service;



import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.chandan.domain.PaymentMethod;
import com.chandan.exception.UserException;
import com.chandan.modal.PaymentOrder;
import com.chandan.payload.dto.BookingDTO;
import com.chandan.payload.dto.UserDTO;
import com.chandan.payload.response.PaymentLinkResponse;


public interface PaymentService {

    PaymentLinkResponse createOrder(UserDTO user,
                                    BookingDTO booking, PaymentMethod paymentMethod) throws RazorpayException, UserException, StripeException;

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    PaymentOrder getPaymentOrderByPaymentId(String paymentId) throws Exception;

    Boolean ProceedPaymentOrder (PaymentOrder paymentOrder,
                                 String paymentId, String paymentLinkId) throws RazorpayException;

    PaymentLink createRazorpayPaymentLink(UserDTO user,
                                          Long Amount,
                                          Long orderId) throws RazorpayException;

    String createStripePaymentLink(UserDTO user, Long Amount,
                                            Long orderId) throws StripeException;
}
