package th.go.oae.rcmo.Model;

/**
 * Created by Taweesin on 25/6/2559.
 */
public class STDVarModel {
    public  String name;
    public  String value;
    public  String unit;

    public STDVarModel(String name, String value, String unit) {
        this.name = name;
        this.value = value;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "STDVarModel{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
