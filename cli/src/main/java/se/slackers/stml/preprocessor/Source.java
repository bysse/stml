package se.slackers.stml.preprocessor;

import se.slackers.stml.mapper.SourcePositionMapper;

import java.nio.file.Path;

public class Source {
    private String code;
    private Path path;
    private SourcePositionMapper mapper;

    public Source(String code, Path path, SourcePositionMapper mapper) {
        this.code = code;
        this.path = path;
        this.mapper = mapper;
    }

    public String getCode() {
        return code;
    }

    public Path getPath() {
        return path;
    }

    public SourcePositionMapper getMapper() {
        return mapper;
    }
}
