package br.sp.parksguide.util;

import java.io.IOException;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;



@Service
public class FirebaseUtil {
	// VARIÁVEL PARA GUARDAR AS CREDENCIAIS DO FIREBASE
	private Credentials credenciais;
	// VARIÁVEL PARA ACESSAR O STORAGE
	private Storage storage;
	// CONSTANTE PARA O NOME DO BUCKET
	private final String BUCKET_NAME = "parksguide-a70d9.appspot.com";
	// CONSTANTE PARA O PREFIXO DA URL
	private final String PREFIX = "https://firebasestorage.googleapis.com/v0/b/"+BUCKET_NAME+"/o/";
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
	
	public String uploadFile(MultipartFile arquivo) throws IOException {
		// GERA UMA STRING ALEATÓRIA PARA O NOME DO ARQUIVO
		String nomeArquivo = UUID.randomUUID().toString() + getExtensao(arquivo.getOriginalFilename());
		
		// CRIAR UMA BlobId
		BlobId blobId = BlobId.of(BUCKET_NAME, nomeArquivo);
		// CRIAR UM BlobInfo A PARTIR DO BlodId
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
		// MANDA P BlobInfo PARA O STORAGE PASSANDO OS BYTES DO ARQUIVO PRA ELE
		storage.create(blobInfo,  arquivo.getBytes());
		
		// RETORNAR A URL PARA ACESSAR O ARQUIVO
		return String.format(DOWLOAD_URL, nomeArquivo);
	}
	
	// RETORNA A EXTENSÃO DE UM ARQUIVO ATRAVÉS DO SEU NOME
	public String getExtensao(String nomeArquivo) {
		// RETORNA O TRECHO DA QUE STRING QUE VAI DO ÚLTIMO PONTO ATÉ O FIM
		return nomeArquivo.substring(nomeArquivo.lastIndexOf('.'));
	}
	
	// MÉTODOS PARA EXCLUIR A FOTO DO FIREBASE
	public void deletar(String nomeArquivo) {
		// RETIRA O PREFIXO E SUFIXO DO NOME DO ARQUIVO
		nomeArquivo = nomeArquivo.replace(PREFIX, "").replace(SUFFIX, "");
		// PEGA O BLOB ATRAVÉS DO NOME DO ARQUIVO
		Blob blob = storage.get(BlobId.of(BUCKET_NAME, nomeArquivo));
		// DELETA O ARQUIVO
		storage.delete(blob.getBlobId());
	}
	
	
	
}
