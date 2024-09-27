package vm.vmjavateste.service;

import com.vm.java.teste.vmjavateste.service.enums.TipoEnvioEmail;
import com.vm.java.teste.vmjavateste.service.EmailService;
import com.vm.java.teste.vmjavateste.util.MensagemUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class EmailServiceTest {

    private EmailService emailService;
    private MensagemUtil mensagemUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:label");
        messageSource.setDefaultEncoding("UTF-8");
        mensagemUtil = new MensagemUtil(messageSource);
        emailService = new EmailService();
    }


    /*
    Teste generico somente para printar que está enviando email
     */
    @Test
    public void testEnviaEmailCriacaoConta() {
        emailService.enviaEmail("email@example.com", "Nome", TipoEnvioEmail.CRIACAO);

    }
    /*
    Teste generico somente para printar que está enviando email
     */
    @Test
    public void testEnviaEmailAlteracao() {
        emailService.enviaEmail("email@example.com", "Nome", TipoEnvioEmail.ALTERACAO);
        // Verificações
    }
}