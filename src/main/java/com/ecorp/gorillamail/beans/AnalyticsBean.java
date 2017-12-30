package com.ecorp.gorillamail.beans;

import com.ecorp.gorillamail.common.ViewIds;
import com.ecorp.gorillamail.entities.ExternalResource;
import com.ecorp.gorillamail.entities.Template;
import com.ecorp.gorillamail.entities.VisitorInformation;
import com.ecorp.gorillamail.services.TemplateService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named( value = "analyticsBean" )
@SessionScoped
public class AnalyticsBean implements Serializable {
    @Inject
    private TemplateService templateService;

    @Getter
    private ExternalResource[] links;

    @Getter
    private int mobileCount;

    @Getter
    private int desktopCount;

    private boolean isMobileVisitor(VisitorInformation visitor) {
        final String userAgent = visitor.getUserAgent().toLowerCase();
        final boolean isAndroid = userAgent.contains("android"),
                      isIPhone = userAgent.contains("iphone"),
                      isIPad = userAgent.contains("ipad");

        return isAndroid || isIPhone || isIPad;
    }

    private void calculateMobileDesktopRatio() {
        mobileCount = desktopCount = 0;

        for (ExternalResource resource : links) {
            for (VisitorInformation visitor : resource.getVisitors()) {
                if (isMobileVisitor(visitor)) {
                    mobileCount++;
                } else {
                    desktopCount++;
                }
            }
        }
    }

    public String viewAnalytics(Template template) {
        template = templateService.fetchTemplate(template);
        links = template.getLinks().stream().toArray(ExternalResource[]::new);

        calculateMobileDesktopRatio();

        return ViewIds.ANALYTICS;
    }
}
