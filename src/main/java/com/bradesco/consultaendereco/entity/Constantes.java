package com.bradesco.consultaendereco.entity;

import com.bradesco.consultaendereco.entity.enums.Regiao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.bradesco.consultaendereco.entity.enums.Regiao.*;


public class Constantes {

    public static final Map<Regiao, String> REGIAO_ESTADO = new HashMap<>() {{
        put(SUL, "RS, SC, PR");
        put(SUDESTE, "SP, RJ, ES, MG");
        put(CENTRO_OESTE, "MT, GO, MS");
        put(NORDESTE, "MA, PI, CE, RN, PB, PE, AL, SE, BA");
        put(NORTE, "AC, AM, RO, RR, AP, PA, TO");
    }};

    public static final Map<Regiao, BigDecimal> REGIAO_PRECO = new HashMap<>() {{
        put(SUL, new BigDecimal("17.30"));
        put(SUDESTE,  new BigDecimal("7.85"));
        put(CENTRO_OESTE,  new BigDecimal("23.50"));
        put(NORDESTE,  new BigDecimal("15.98"));
        put(NORTE,  new BigDecimal("20.83"));
    }};
}
