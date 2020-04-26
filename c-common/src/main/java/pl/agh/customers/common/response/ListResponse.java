package pl.agh.customers.common.response;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ListResponse {
    private List list;
    private int count;
}
