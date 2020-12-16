package se.slackers.stml.preprocessor;

import se.slackers.stml.mapper.SourcePositionMapper;

public class Source {
    private String code;
    private SourcePositionMapper mapper;

    public Source(String code, SourcePositionMapper mapper) {
        this.code = code;
        this.mapper = mapper;
    }

    public String getCode() {
        return code;
    }

    public SourcePositionMapper getMapper() {
        return mapper;
    }
}
