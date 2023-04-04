package me.safronov.vyacheslav.webapplicationsforthewarehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Sock {
    private final SocksColor socksColor;
    private final SocksSize socksSize;
    private final Integer cottonPart;

}
