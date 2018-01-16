package com.ecorp.gorillamail.services.external;

import com.ecorp.bannerad.service.Confirmation;
import com.ecorp.bannerad.service.Customer;
import com.ecorp.bannerad.service.Dimension;
import com.ecorp.bannerad.service.OrderService;
import com.ecorp.bannerad.service.OrderService_Service;
import com.ecorp.bannerad.service.PlacingOrder;
import com.ecorp.gorillamail.services.external.exceptions.AdRequestException;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.xml.ws.WebServiceRef;

@ApplicationScoped
@Alternative
public class AdService implements AdServiceIF {
    private Customer gorillamailCustomer;
    private Random random;
    
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/im-lamport_8080/banner_ad-1.0-SNAPSHOT/OrderService.wsdl")
    private OrderService_Service orderServiceRef;
    private OrderService orderService;
    
    @PostConstruct
    public void postConstruct() {
        orderService = orderServiceRef.getOrderServicePort();
        
        gorillamailCustomer = new Customer();
        gorillamailCustomer.setEmail("ads@gorillamail.space");
        
        random = new Random();
    }
    
    @Override
    public String requestBannerUrl() throws AdRequestException {
        final Dimension dimension = new Dimension();
        dimension.setWidth(468);
        dimension.setHeight(60);
        
        final PlacingOrder order = new PlacingOrder();
        order.setDimension(dimension);
        
        try {
            final Confirmation confirmation = orderService.orderBannerPlacement(gorillamailCustomer, order);
            final List<String> banners = confirmation.getUrls();
            final int bannerIndex = random.nextInt(banners.size());
            
            return banners.get(bannerIndex);
        } catch (Exception exception) {
            throw new AdRequestException(exception);
        }
    }
}
