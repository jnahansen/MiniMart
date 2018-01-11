package com.jbheng.minimart.model;

/**
 * The {@link Product} class.
 * <p>Defines the attributes for a product.</p>
 * <p>
 * Details:
 * productId : Unique Id of the product
 * productName : Product Name
 * shortDescription : Short Description of the product
 * longDescription : Long Description of the product
 * price : Product price
 * productImage : Image url for the product
 * reviewRating : Average review rating for the product
 * reviewCount : Number of reviews
 * inStock : Returns true if item in stock
 * <p>
 * Sample:
 * {
 *  "productId":"31e1cb21-5504-4f02-885b-8f267131a93f",
 *  "productName":"VIZIO Class Full-Array LED Smart TV",
 *  "shortDescription":"\u003cul\u003e\n\t\u003cli\u003eResolution: 1080p\u003c/li\u003e\n\t\u003cli\u003eRefresh Rate: 240Hz\u003c/li\u003e\n\t\u003cli\u003eHDMI Inputs: 4\u003c/li\u003e\n\u003c/ul\u003e",
 *  "longDescription":"\u003cul\u003e\n\t\u003cli style=\"font-size: 13px;\"\u003e\u003cb\u003eVIZIO Internet Apps Plus\u003c/b\u003e&reg; &ndash; Instantly enjoy the latest hit movies, TV shows, music and even more premium apps made for the big screen&sup1;\u003c/li\u003e\n\t\u003cli style=\"font-size: 13px;\"\u003e\u003cb\u003eFull-Array LED backlight\u003c/b\u003e &ndash; Distributes LEDs behind the entire screen delivering superior light uniformity and picture performance\u003c/li\u003e\n\t\u003cli style=\"font-size: 13px;\"\u003e\u003cb\u003e32 Active LED Zones\u003c/b\u003e &ndash; Dynamically adjusts the LED backlight per zone creating deeper, pure black levels and higher contrast\u003c/li\u003e\n\t\u003cli style=\"font-size: 13px;\"\u003e\u003cb\u003eClear Action 720\u003c/b\u003e &ndash; Enhanced motion clarity with 240Hz effective refresh rate and powerful image processing for stunning sharp detail in sports and fast action scenes.\u003c/li\u003e\n\t\u003cli style=\"font-size: 13px;\"\u003e\u003cb\u003eActive Pixel Tuning\u003c/b\u003e &ndash; Intelligent pixel-level brightness adjustments for increased picture accuracy and contrast.\u003c/li\u003e\n\t\u003cli style=\"font-size: 13px;\"\u003e\u003cb\u003eBuilt-in Wi-Fi\u003c/b\u003e &ndash; Easily connect to the Internet with ultra-fast 802.11n wireless\u003c/li\u003e\n\t\u003cli style=\"font-size: 13px;\"\u003e\u003cb\u003e1080p Full HD resolution\u003c/b\u003e &ndash; Enjoy high-definition TV with a crystal-clear picture\u003c/li\u003e\n\t\u003cli style=\"font-size: 13px;\"\u003e\u003cb\u003e20 million to 1 DCR\u003c/b\u003e &nbsp;&ndash; Exceptionally high contrast for deeper blacks and more radiant whites.\u003c/li\u003e\n\t\u003cli style=\"font-size: 13px;\"\u003e\u003cb\u003eM-Series Signature Design\u003c/b\u003e &ndash; Give your room a designer upgrade with the ultra-narrow, near borderless design of M-Series\u003c/li\u003e\n\t\u003cli style=\"font-size: 13px;\"\u003e\u003cb\u003eSmart remote with backlit keyboard\u003c/b\u003e &ndash; Easy to search and type, even in the dark\u003c/li\u003e\n\t\u003cli style=\"font-size: 13px;\"\u003e\u003cb\u003e4 HDMI ports\u003c/b\u003e &ndash; Perfect for connecting your high definition entertainment devices to the TV\u003c/li\u003e\n\t\u003cli style=\"font-size: 13px;\"\u003e\u003cb\u003e1 USB ports\u003c/b\u003e &ndash; share photos, music, and video right on the big screen\u003c/li\u003e\n\t\u003cli style=\"font-size: 13px;\"\u003e\u003cb\u003eDTS Studio Sound\u003c/b\u003e &ndash; Advanced virtual surround sound audio from VIZO&rsquo;s two built-in speakers\u003c/li\u003e\n\t\u003cli style=\"font-size: 13px;\"\u003e\u003cb\u003eNew TV settings menu with on-screen user manual\u003c/b\u003e &ndash; More intuitive menu system with quick, on-screen access to the user manual. No need to search for a paper user manual.\u003c/li\u003e\n\t\u003cli style=\"font-size: 13px;\"\u003e\u003cb\u003eAmbient Light Sensor\u003c/b\u003e &ndash; Intelligent backlight sensors automatically adjust to surrounding brightness for a great picture in virtually any room.\u003c/li\u003e\n\t\u003cli style=\"font-size: 13px;\"\u003e\u003cb\u003eEnergy Star 6.0\u003c/b\u003e &nbsp;&ndash; More energy efficient than conventional CCFL-based LCD TVs to save you even more on energy bills\u003c/li\u003e\n\u003c/ul\u003e",
 *  "price":"$878.00",
 *  "productImage":"http://someurl/0084522601078_A",
 *  "reviewRating":0.0,
 *  "reviewCount":0,
 *  "inStock":true
 * }
 */
public class Product {

    private String productId;
    private String productName;
    private String shortDescription;
    private String longDescription;
    private String price;
    private String productImage;
    private float reviewRating;
    private int reviewCount;
    private boolean inStock;

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getPrice() {
        return price;
    }

    public String getProductImage() {
        return productImage;
    }

    public float getReviewRating() {
        return reviewRating;
    }

    public float getReviewCount() {
        return reviewCount;
    }

    public boolean getInStock() {
        return inStock;
    }
}
