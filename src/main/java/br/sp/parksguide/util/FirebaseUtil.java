package br.sp.parksguide.util;

import java.io.IOException;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class FirebaseUtil {
	// VARIÁVEL PARA GUARDAR AS CREDENCIAIS DO FIREBASE
	private Credentials credenciais;
	// VARIÁVEL PARA ACESSAR O STORAGE
	private Storage storage;
	// CONSTANTE PARA O NOME DO BUCKET
	private final String BUCKET_NAME = "parksguide-a70d9.appspot.com/";
	// CONSTANTE PARA O PREFIXO DA URL
	private final String PREFIX = "http://firebasestorage.googleapis.com/v0/b/"+BUCKET_NAME+"/o/";
	// CONSTANTE PARA O SUFFIXO DA URL
	private final String SUFFIX = "?alt=media";
	// CONSTANTE PARA A URL
	private final String DOWLOAD_URL = PREFIX + "%s" + SUFFIX;
	
	public FirebaseUtil() {
		// BUSCAR AS CREDENCIAIS (ARQUIVO JSON)
		Resource resource = new ClassPathResource("chaveFirebase.json");
		 
		try {
			// LER O ARQUIVO PARA OBTER AS CREDENCIAIS
			credenciais = GoogleCredentials.fromStream(resource.getInputStream());
			// ACESSA O SERVIÇO DE STORAGE
			storage = StorageOptions.newBuilder().setCredentials(credenciais).build().getService();
			
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		
	}
	
	public String uploadFile(MultipartFile arquivo) {
		// GERA UMA STRING ALEATÓRIA PARA O NOME DO ARQUIVO
		String nomeArquivo = UUID.randomUUID().toString() + getExtensao(arquivo.getOriginalFilename());
		
		return nomeArquivo;
	}
	
	// RETORNA A EXTENSÃO DE UM ARQUIVO ATRAVÉS DO SEU NOME
	public String getExtensao(String nomeArquivo) {
		// RETORNA O TRECHO DA QUE STRING QUE VAI DO ÚLTIMO PONTO ATÉ O FIM
		return nomeArquivo.substring(nomeArquivo.lastIndexOf('.'));
	}
	
	
	
}
