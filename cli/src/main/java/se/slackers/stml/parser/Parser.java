package se.slackers.stml.parser;

import se.slackers.stml.STMLLexer;
import se.slackers.stml.STMLParser;
import se.slackers.stml.error.ErrorCode;
import se.slackers.stml.io.FileSystemIO;
import se.slackers.stml.mapper.SourcePosition;
import se.slackers.stml.mapper.SourcePositionId;
import se.slackers.stml.mapper.SourceRangeId;
import se.slackers.stml.model.Registry;
import se.slackers.stml.preprocessor.Source;
import se.slackers.stml.typechecking.TypeCheckingVisitor;
import org.antlr.v4.runtime.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Parser {
    private final Logger logger = Logger.getLogger(Parser.class.getName());

    public ParserResult parse(Source source) {
        final CharStream input = CharStreams.fromString(source.getCode());
        final STMLLexer lexer = new STMLLexer(input);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final STMLParser parser = new STMLParser(tokens);
        final List<ParserError> errors = new ArrayList<>();

        final Registry registry = new Registry();

        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                SourcePosition position = SourcePosition.create(line, charPositionInLine);
                SourcePositionId positionId = source.getMapper().map(position);
                errors.add(new ParserError(SourceRangeId.create(positionId), ErrorCode.SYNTAX_ERROR, msg));
                logger.severe("Syntax Error: " + msg);
            }
        });
        parser.setErrorHandler(new DefaultErrorStrategy());

        logger.info("Parsing source");
        STMLParser.Statement_listContext context = parser.statement_list();

        if (errors.isEmpty()) {
            logger.info("Running type checking");
            TypeCheckingVisitor typeChecking = new TypeCheckingVisitor(source.getMapper(), new FileSystemIO(), registry);
            context.accept(typeChecking);

            errors.addAll(typeChecking.getErrors());
        }

        return new ParserResult(
                context,
                registry,
                errors
        );
    }
}
