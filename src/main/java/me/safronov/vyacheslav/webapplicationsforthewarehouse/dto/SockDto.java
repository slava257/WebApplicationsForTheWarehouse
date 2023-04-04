package me.safronov.vyacheslav.webapplicationsforthewarehouse.dto;

import lombok.Data;
import me.safronov.vyacheslav.webapplicationsforthewarehouse.model.SocksColor;
import me.safronov.vyacheslav.webapplicationsforthewarehouse.model.SocksSize;
@Data
public class SockDto {
        private final SocksColor socksColor;
        private final SocksSize socksSize;
        private final Integer cottonPart;
        private final int quantity;

    public SockDto(SocksColor socksColor, SocksSize socksSize, Integer cottonPart, Integer quantity) {
        this.socksColor = socksColor;
        this.socksSize = socksSize;
        this.cottonPart = cottonPart;
        this.quantity = quantity;
    }
}
