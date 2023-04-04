package me.safronov.vyacheslav.webapplicationsforthewarehouse.service;

import me.safronov.vyacheslav.webapplicationsforthewarehouse.dto.SockDto;
import me.safronov.vyacheslav.webapplicationsforthewarehouse.exception.InsufficientSockQuantityException;
import me.safronov.vyacheslav.webapplicationsforthewarehouse.exception.InvalidSocksRequestException;
import me.safronov.vyacheslav.webapplicationsforthewarehouse.model.Sock;
import me.safronov.vyacheslav.webapplicationsforthewarehouse.model.SocksColor;
import me.safronov.vyacheslav.webapplicationsforthewarehouse.model.SocksSize;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

@Service
public class SocksService {
    private final Map<Sock, Integer> socks = new HashMap<>();

    public void addSocks(SockDto sockDto) {
        validateRequest(sockDto);
        Sock sock = new Sock(sockDto.getSocksColor(), sockDto.getSocksSize(), sockDto.getCottonPart());
        if (socks.containsKey(sock)) {
            socks.put(sock, socks.get(sock) + sockDto.getQuantity());
        } else {
            socks.put(sock, sockDto.getQuantity());
        }
    }

    public void releaseOfSocksFromTheWarehouse(SockDto sockDto) throws InsufficientSockQuantityException {
        validateRequest(sockDto);
        Sock sock = new Sock(sockDto.getSocksColor(), sockDto.getSocksSize(), sockDto.getCottonPart());
        int sockQuantity = socks.getOrDefault(sock, 0);
        if (sockQuantity >= sockDto.getQuantity()) {
            socks.put(sock, socks.get(sock) - sockDto.getQuantity());
        } else {
            throw new InsufficientSockQuantityException(" Не коректное значения ");
        }
    }

    public int getSockQuantity(SocksColor socksColor, SocksSize socksSize, Integer cottonPartMin, Integer cottonPartMax) {
        int total = 0;
        for (Map.Entry<Sock, Integer> mapSock : socks.entrySet()) {
            if (socksColor == null && !mapSock.getKey().getSocksColor().equals(socksColor)) {
                continue;
            }
            if (socksSize == null && !mapSock.getKey().getSocksSize().equals(socksSize)) {
                continue;
            }
            if (cottonPartMin == null && mapSock.getKey().getCottonPart() < cottonPartMin) {
                continue;
            }
            if (cottonPartMax == null && mapSock.getKey().getCottonPart() > cottonPartMax) {
                continue;
            }
            total += mapSock.getValue();
        }
        return total;
    }
    public void deleteSock(SockDto sockDto) {
        validateRequest(sockDto);
        Sock sock = new Sock(sockDto.getSocksColor(), sockDto.getSocksSize(), sockDto.getCottonPart());
        int sockQuantity = socks.getOrDefault(sock, 0);
        if (sockQuantity >= sockDto.getQuantity()) {
            socks.put(sock, socks.get(sock) - sockDto.getQuantity());
        } else {
            throw new InsufficientSockQuantityException(" Не корктные значения ");
        }
    }
    private void validateRequest(SockDto sockDto)   {
        if (sockDto.getSocksColor() == null || sockDto.getSocksSize() == null) {
            throw new InvalidSocksRequestException(" Цвет и размер равны нулю");
        }
        if (sockDto.getQuantity() <= 0) {
            throw new InvalidSocksRequestException(" Количество носков не может быть меньше нуля");
        }
        if (sockDto.getCottonPart() < 0 || sockDto.getCottonPart() > 100) {
            throw new InvalidSocksRequestException(" процентное содержание хлопка в составе носков не может быть меньше нуля или больше 100%");
        }
    }
}