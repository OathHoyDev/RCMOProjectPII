package th.go.oae.rcmo.Model;

/**
 * Created by Taweesin on 6/15/2016.
 */
public class ProductModel {
    private String prdName;
    private int prdID;
    private int prdGrpID;
    private int plantGrpID;
    private int riceTypeID;
    private String prodHierarchy;


    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    public int getPrdID() {
        return prdID;
    }

    public void setPrdID(int prdID) {
        this.prdID = prdID;
    }

    public int getPrdGrpID() {
        return prdGrpID;
    }

    public void setPrdGrpID(int prdGrpID) {
        this.prdGrpID = prdGrpID;
    }

    public int getPlantGrpID() {
        return plantGrpID;
    }

    public void setPlantGrpID(int plantGrpID) {
        this.plantGrpID = plantGrpID;
    }

    public int getRiceTypeID() {
        return riceTypeID;
    }

    public void setRiceTypeID(int riceTypeID) {
        this.riceTypeID = riceTypeID;
    }

    public String getProdHierarchy() {
        return prodHierarchy;
    }

    public void setProdHierarchy(String prodHierarchy) {
        this.prodHierarchy = prodHierarchy;
    }

    public ProductModel(int prdID, String prdName, int prdGrpID, int plantGrpID, int riceTypeID, String prodHierarchy) {
        this.prdID = prdID;
        this.prdName = prdName;
        this.prdGrpID = prdGrpID;
        this.plantGrpID = plantGrpID;
        this.riceTypeID = riceTypeID;
        this.prodHierarchy = prodHierarchy;
    }

/*
    public ProductModel(int prdID, String prdName, int prdGrpID, int plantGrpID, int riceTypeID, String prodHierarchy ) {
        PrdID = prdID;
        PrdName = prdName;
        PrdGrpID = prdGrpID;
        PlantGrpID = plantGrpID;
        RiceTypeID = riceTypeID;
        ProdHierarchy=prodHierarchy;
    }

*/




}
