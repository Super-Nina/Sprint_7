package model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderModel {
    private  String firstName;
    private  String lastName;
    private  String address;
    private  int metroStation;
    private  String phone;
    private  int rentTime;
    private  String deliveryDate;
    private  String comment;
    private List<String> color;
}
