package server.handlers;

import server.service.ClearApplicationService;

public class ClearApplicationHandler extends Handlers {
    ClearApplicationService clearApplicationService = new ClearApplicationService();

    public ClearApplicationHandler() {
    }

    public void clearAll() {
        clearApplicationService.clearAll();
    }
}
