package jwg.eliteinventory.mainactivities;

/** Created by John **/
public class ListItems {

    private String barcode;
    private int imgId;

    public ListItems(String barcode, int imgId) {
        this.barcode = barcode;
        this.imgId = imgId;
    }


    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }



}
