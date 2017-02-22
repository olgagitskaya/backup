package by.catalog.service.factory;

import by.catalog.service.CatalogService;
import by.catalog.service.impl.CatalogServiceImpl;

/**
 * Created by Volha_Hitskaya on 1/30/2017.
 */
public final class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private final CatalogService catalogService = new CatalogServiceImpl();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public CatalogService getCatalogService() {
        return catalogService;
    }
}
