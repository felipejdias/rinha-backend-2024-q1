package com.felipejdias.rinhabackend2024q1.db

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service


@Service
class DataInitializer {

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @PostConstruct
    fun initializeData() {

        //Dados iniciais
        val sql = """ 
                    DO $$
                        BEGIN
                            INSERT INTO Client (client_id, name, credit_limit, balance) VALUES
                                (1, 'o barato sai caro', 1000 * 100, 0),
                                (2, 'zan corp ltda', 800 * 100, 0),
                                (3, 'les cruders', 10000 * 100, 0),
                                (4, 'padaria joia de cocaia', 100000 * 100, 0),
                                (5, 'kid mais', 5000 * 100, 0);
                        END $$;
                    """

        //QUERY para remover dados caso necessário
        val sqlCleanClient = """
                        DO $$
                        BEGIN
                            DELETE FROM CLIENT;
                        END $$;
                    """

        //QUERY para remover dados caso necessário
        val sqlCleanTransactions = """
                        DO $$
                        BEGIN
                            DELETE FROM transactions;
                        END $$;
                    """
        //deixar comentado após a primeira execução
        //jdbcTemplate.execute(sqlCleanTransactions)
          //jdbcTemplate.execute(sqlCleanClient)
        //jdbcTemplate.execute(sql)
    }
}