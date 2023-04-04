package me.safronov.vyacheslav.webapplicationsforthewarehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.safronov.vyacheslav.webapplicationsforthewarehouse.dto.SockDto;
import me.safronov.vyacheslav.webapplicationsforthewarehouse.service.SocksService;

import me.safronov.vyacheslav.webapplicationsforthewarehouse.exception.InsufficientSockQuantityException;
import me.safronov.vyacheslav.webapplicationsforthewarehouse.model.SocksColor;
import me.safronov.vyacheslav.webapplicationsforthewarehouse.model.SocksSize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//- Веб-приложение выполнено в виде RESTful-сервиса.
//- Веб-приложение основано на Spring(Boot) Framework.
//- Написаны все CRUD-методы :
//    - POST — на склад можно добавить новый товар;
//    - PUT — можно забрать товар со склада;
//    - GET — можно получить данные о товаре на складке: общее количество и данные по составу;
//    - DELETE — со склада можно списать бракованный товар.
//- Все CRUD-методы работают и возвращают значения согласно условию задания.
//- Отработаны ошибки при некорректно введенных данных.
//- Переменные, объекты, классы и методы имеют корректные названия согласно правилам написания кода в языке Java.
//- В качестве UI-части приложения используется API-клиент (Swagger-ui).
//- Задание оформлено и сдано на платформу как GitHub-репозиторий.

@RestController
@RequestMapping("/socks")
@Tag(name = "Работа со складом", description = "Операции СRUD и другии эндопоинты для работы со складом насков")
public class SocksController {


    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @ExceptionHandler(InsufficientSockQuantityException.class)
    public ResponseEntity<String> handleInvalidException(InsufficientSockQuantityException invalidSockRequestException) {
        return ResponseEntity.badRequest().body(invalidSockRequestException.getMessage());
    }

    @PostMapping("/api")
    @Operation(summary = "Регистрирует приход носков на склад",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удолось добавить приход",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Параметры запроса отсутствуют или имеют некорректный формат",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Произошла ошибка, не зависящая от вызывающей стороны",
                            content = @Content
                    )
            })

    public void registersTheArrivalOfTheGoods(@RequestBody SockDto sockDto) {

        socksService.addSocks(sockDto);
    }


    @PutMapping()
    @Operation(summary = "Регистрирует отпуск носков на склад",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "удалось произвести отпуск носков со склада",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "товара нет на складе в нужном количестве или параметры запроса имеют некорректный формат",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Произошла ошибка, не зависящая от вызывающей стороны",
                            content = @Content
                    )
            })
    public void releaseOfSocksFromTheWarehouse(@RequestBody SockDto sockDto) {
        try {
            socksService.releaseOfSocksFromTheWarehouse(sockDto);
        } catch (InsufficientSockQuantityException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping()
    @Operation(summary = "Списать насок по браку",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "запрос выполнен, товар списан со склада",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Параметры запроса отсутствуют или имеют некорректный формат",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Произошла ошибка, не зависящая от вызывающей стороны",
                            content = @Content
                    )
            })
    public void removeDefectiveSocks(@RequestBody SockDto sockDto) {
        socksService.deleteSock(sockDto);
    }
    @GetMapping()
    @Operation(summary = "Получить уоличество товара на складе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "запрос выполнен, результат в теле ответа в виде строкового представления",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Параметры запроса отсутствуют или имеют некорректный формат",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Произошла ошибка, не зависящая от вызывающей стороны",
                            content = @Content
                    )
            })
    public int getSocks(@RequestParam(required = false, name = "sockColor")SocksColor socksColor,
                        @RequestParam(required = false, name = "sockSize") SocksSize socksSize,
                        @RequestParam(required = false, name = "cottonPartMin")Integer cottonPartMin,
                        @RequestParam(required = false, name = "cottonPartMax")Integer cottonPartMax) {
        return socksService.getSockQuantity(socksColor, socksSize, cottonPartMin, cottonPartMax);
    }
}
