package com.adCampaigh.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by daweizhuang on 7/15/16.
 */
public class AdCampaigh {
    private String adContent;
    private String partnerId;

    @JsonProperty(value = "ad_content")
    public String getAdContent() {
        return adContent;
    }

    public void setAdContent(String adContent) {
        this.adContent = adContent;
    }

    @JsonProperty(value = "partner_id")
    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
}
