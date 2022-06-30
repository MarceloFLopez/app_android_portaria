package br.com.appportaria.application.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.spi.service.ServiceFinder;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import br.com.appportaria.application.domain.TabEmpresa;
import br.com.appportaria.application.domain.TabOperacao;
import br.com.appportaria.application.domain.TabPessoa;
import br.com.appportaria.application.domain.TabTransporte;
import br.com.appportaria.application.domain.Usuario;
import br.com.appportaria.application.util.AndroidServiceIteratorProvider;
import br.com.appportaria.application.util.DateDeserializer;
import br.com.appportaria.application.util.DateSerializer;

public class ConnRest {

    private final String HOSTNAME = "http://192.168.2.145:8080/";
    private final String API = "portaria-wrest/rest/";

    //PATHs
    private final String PATH = "tabempresaapi/";
    private final String PATH1 = "tabpessoaapi/";
    private final String PATH2 = "taboperacaoapi/";
    private final String PATH3 = "tabtransporteapi/";
    private final String PATH4 = "usuarioapi/";

    public List<TabEmpresa> listEmpresaAll() {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Gson gson = new Gson();
        Client c = Client.create();
        // GET example
        WebResource wr = c.resource(HOSTNAME + API + PATH + "listAll/");
        String json = wr.get(String.class);
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
        List<TabEmpresa> t = gson.fromJson(json, new TypeToken<List<TabEmpresa>>() {
        }.getType());
        return t;
    }
    public void salvarEmpresa(TabEmpresa objeto) {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Gson gson = new Gson();
        Client c = Client.create();
        WebResource wr = c.resource(HOSTNAME + API + PATH + "salvar/").path("objeto");
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateSerializer()).create();
        wr.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class,
                gson.toJson(objeto));
    }
    public String removeEmpresa(String id) throws Exception {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Client c = Client.create();
        WebResource wr = c.resource(HOSTNAME + API + PATH1 + "remove/" + id);
        String s = wr.accept(MediaType.APPLICATION_XML).delete(String.class);
        return s;
    }
    public TabEmpresa findEmpresaName(String value) {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Gson gson = new Gson();
        Client c = Client.create();
        // GET example
        WebResource wr = c.resource(HOSTNAME + API + PATH + "findColumnName/" + value);
        String json = wr.get(String.class);
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
        TabEmpresa t = gson.fromJson(json, new TypeToken<TabEmpresa>() {
        }.getType());
        return t;
    }
    public TabEmpresa findEmpresaId(String id) {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Gson gson = new Gson();
        Client c = Client.create();
        // GET example
        WebResource wr = c.resource(HOSTNAME + API + PATH + "findId/" + id);
        String json = wr.get(String.class);
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
        TabEmpresa t = gson.fromJson(json, new TypeToken<TabEmpresa>() {
        }.getType());
        return t;
    }

    public List<TabPessoa> listPessoaAll() {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Gson gson = new Gson();
        Client c = Client.create();
        // GET example
        WebResource wr = c.resource(HOSTNAME + API + PATH1 + "listAll/");
        String json = wr.get(String.class);
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
        List<TabPessoa> t = gson.fromJson(json, new TypeToken<List<TabPessoa>>() {
        }.getType());
        return t;
    }
    public void salvarPessoa(TabPessoa objeto) {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Gson gson = new Gson();
        Client c = Client.create();
        WebResource wr = c.resource(HOSTNAME + API + PATH1 + "salvar/").path("objeto");
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateSerializer()).create();
        wr.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class,
                gson.toJson(objeto));
    }
    public String removerPessoa(String id) throws Exception {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Client c = Client.create();
        WebResource wr = c.resource(HOSTNAME + API + PATH1 + "remover/" + id);
        String s = wr.accept(MediaType.APPLICATION_XML).delete(String.class);
        return s;
    }
    public TabPessoa findColumnPessoa(String value) {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Gson gson = new Gson();
        Client c = Client.create();
        // GET example
        WebResource wr = c.resource(HOSTNAME + API + PATH1 + "findColumnName/" + value);
        String json = wr.get(String.class);
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
        TabPessoa t = gson.fromJson(json, new TypeToken<TabPessoa>() {
        }.getType());
        return t;
    }
    public TabPessoa findPessoaId(String id) {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Gson gson = new Gson();
        Client c = Client.create();
        // GET example
        WebResource wr = c.resource(HOSTNAME + API + PATH1 + "findId/" + id);
        String json = wr.get(String.class);
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
        TabPessoa t = gson.fromJson(json, new TypeToken<TabPessoa>() {
        }.getType());
        return t;
    }

    public List<TabOperacao> listOperacaoAll() {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Gson gson = new Gson();
        Client c = Client.create();
        // GET example
        WebResource wr = c.resource(HOSTNAME + API + PATH2 + "listAll/");
        String json = wr.get(String.class);
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
        List<TabOperacao> t = gson.fromJson(json, new TypeToken<List<TabOperacao>>() {
        }.getType());
        return t;
    }
    public void salvarOperacao(TabOperacao objeto) {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Gson gson = new Gson();
        Client c = Client.create();
        WebResource wr = c.resource(HOSTNAME + API + PATH2 + "salvar/").path("objeto");
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateSerializer()).create();
        wr.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class,
                gson.toJson(objeto));
    }
    public String removerOperacao(String id) throws Exception {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Client c = Client.create();
        WebResource wr = c.resource(HOSTNAME + API + PATH2 + "remover/" + id);
        String s = wr.accept(MediaType.APPLICATION_XML).delete(String.class);
        return s;
    }
    public TabOperacao findColumnModel(String value) {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Gson gson = new Gson();
        Client c = Client.create();
        // GET example
        WebResource wr = c.resource(HOSTNAME + API + PATH2 + "findColumnName/" + value);
        String json = wr.get(String.class);
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
        TabOperacao t = gson.fromJson(json, new TypeToken<TabOperacao>() {
        }.getType());
        return t;
    }
    public TabOperacao findId(String id) {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Gson gson = new Gson();
        Client c = Client.create();
        // GET example
        WebResource wr = c.resource(HOSTNAME + API + PATH2 + "findId/" + id);
        String json = wr.get(String.class);
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
        TabOperacao t = gson.fromJson(json, new TypeToken<TabOperacao>() {
        }.getType());
        return t;
    }


    public List<TabTransporte> listTransporteAll() {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Gson gson = new Gson();
        Client c = Client.create();
        // GET example
        WebResource wr = c.resource(HOSTNAME + API + PATH3 + "listAll/");
        String json = wr.get(String.class);
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
        List<TabTransporte> t = gson.fromJson(json, new TypeToken<List<TabTransporte>>() {
        }.getType());
        return t;
    }

    public TabTransporte findTransporteName(String value) {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Gson gson = new Gson();
        Client c = Client.create();
        // GET example
        WebResource wr = c.resource(HOSTNAME + API + PATH3 + "findColumnName/" + value);
        String json = wr.get(String.class);
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
        TabTransporte t = gson.fromJson(json, new TypeToken<TabTransporte>() {
        }.getType());
        return t;
    }

    public TabTransporte findTrasporteId(String id) {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Gson gson = new Gson();
        Client c = Client.create();
        // GET example
        WebResource wr = c.resource(HOSTNAME + API + PATH3 + "findId/" + id);
        String json = wr.get(String.class);
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
        TabTransporte t = gson.fromJson(json, new TypeToken<TabTransporte>() {
        }.getType());
        return t;
    }

    public void salvarTransporte(TabTransporte objeto) {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Gson gson = new Gson();
        Client c = Client.create();
        WebResource wr = c.resource(HOSTNAME + API + PATH3 + "salvar/").path("objeto");
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateSerializer()).create();
        wr.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class,
                gson.toJson(objeto));
    }

    public void atualizarTransporte(TabTransporte objeto) {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Gson gson = new Gson();
        Client c = Client.create();
        WebResource wr = c.resource(HOSTNAME + API + PATH3 + "atualizar/").path("objeto");
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateSerializer()).create();
        wr.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class,
                gson.toJson(objeto));
    }

    public String removerTransporte(String id) throws Exception {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Client c = Client.create();
        WebResource wr = c.resource(HOSTNAME + API + PATH3 + "remover/" + id);
        String s = wr.accept(MediaType.APPLICATION_XML).delete(String.class);
        return s;
    }

    public List<Usuario> listUsuariosAll() {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Gson gson = new Gson();
        Client c = Client.create();
        // GET example
        WebResource wr = c.resource(HOSTNAME + API + PATH4 + "listAll/");
        String json = wr.get(String.class);
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
        List<Usuario> t = gson.fromJson(json, new TypeToken<List<Usuario>>() {
        }.getType());
        return t;
    }

    public Usuario findColumnUsuarios(String value) {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Gson gson = new Gson();
        Client c = Client.create();
        // GET example
        WebResource wr = c.resource(HOSTNAME + API + PATH4 + "findColumnName/" + value);
        String json = wr.get(String.class);
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
        Usuario t = gson.fromJson(json, new TypeToken<Usuario>() {
        }.getType());
        return t;
    }

    public Usuario findUsuariosId(String id) {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Gson gson = new Gson();
        Client c = Client.create();
        // GET example
        WebResource wr = c.resource(HOSTNAME + API + PATH4 + "findId/" + id);
        String json = wr.get(String.class);
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
        Usuario t = gson.fromJson(json, new TypeToken<Usuario>() {
        }.getType());
        return t;
    }

    public void salvarUsuarios(Usuario objeto) {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Gson gson = new Gson();
        Client c = Client.create();
        WebResource wr = c.resource(HOSTNAME + API + PATH4 + "salvar/").path("objeto");
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateSerializer()).create();
        wr.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class,
                gson.toJson(objeto));
    }

    public void atualizarUsuarios(Usuario objeto) {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Gson gson = new Gson();
        Client c = Client.create();
        WebResource wr = c.resource(HOSTNAME + API + PATH4 + "atualizar/").path("objeto");
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateSerializer()).create();
        wr.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class,
                gson.toJson(objeto));
    }

    public String removerUsuarios(String id) throws Exception {
        ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
        Client c = Client.create();
        WebResource wr = c.resource(HOSTNAME + API + PATH4 + "remover/" + id);
        String s = wr.accept(MediaType.APPLICATION_XML).delete(String.class);
        return s;
    }

}
