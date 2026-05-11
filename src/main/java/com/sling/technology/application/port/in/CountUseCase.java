package com.sling.technology.application.port.in;

import com.sling.technology.domain.model.SearchCount;

public interface CountUseCase {
    SearchCount execute(String searchId);
}
