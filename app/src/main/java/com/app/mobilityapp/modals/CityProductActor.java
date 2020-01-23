package com.app.mobilityapp.modals;

/**
 * Created by Neha on 6/24/2017.
 */

public class CityProductActor {

        private String name;
        private String image;
        private String description;
        private String price;
        private String productnamecode;
        private String productId;
    private boolean isSelected;


        public CityProductActor() {
        }

        public CityProductActor(String name, String type,String image, String pri, String pro_namecode, String productId,boolean isSelected) {
            this.name = name;
            this.image = image;
            this.description= type;
            this.price=pri;
            this.productnamecode=pro_namecode;
            this.productId=productId;
            this.isSelected=isSelected;

        }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }


        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProductnamecode() {
            return productnamecode;
        }

        public void setProductnamecode(String productname) {
            this.productnamecode = productname;
        }
    }


