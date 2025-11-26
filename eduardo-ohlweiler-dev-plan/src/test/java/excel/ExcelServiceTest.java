package excel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import edu.dev.app.SpringBootApp;
import edu.dev.repository.ProdutoRepository;
import edu.dev.service.ExcelService;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = { SpringBootApp.class })
public class ExcelServiceTest {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ExcelService excelService;
	
	@Test
	void lerTabelaExcel() throws Exception {
		
		produtoRepository.deleteAll();
		
		
		/*Ler o arquivo*/
		InputStream excel = getClass().getResourceAsStream("/excel/produtos.xlsx");
		
		MockMultipartFile file = new MockMultipartFile("file", 
				"produtos.xlsx", 
				 "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
				 excel);
		
		excelService.lerTabelaExcel(file);
		
		assertEquals(2, produtoRepository.findAll().size());
		
	}

}
