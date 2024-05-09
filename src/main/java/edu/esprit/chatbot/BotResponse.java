package edu.esprit.chatbot;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.util.Span;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.BagOfWordsFeatureGenerator;
import opennlp.tools.util.TrainingParameters;
import opennlp.tools.util.model.ModelUtil;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.FeatureGenerator;
import opennlp.tools.lemmatizer.LemmatizerME;
import opennlp.tools.lemmatizer.LemmatizerModel;

public class BotResponse {
//    public static void main(String[] args) {
//        String text = "Books about AI to read";
//        try {
//            String[] sents = detectSentences(text);
//            String[] tokens = tokenize(text);
//            String[] posTags = posTag(tokens);
//           // String[] lemmas = lemmatize(tokens, posTags);
//            DoccatModel model = trainCategorizerModel();
//            String category = categorize(model, tokens);
//            System.out.println(text);
//            for (String sentence : sents) System.out.print(" | " + sentence);
//            System.out.println("\n");
//            for (String token : tokens) System.out.print(" | " + token);
//            System.out.println("\n");
//            for (String tag : posTags) System.out.print(" | " + tag);
//            System.out.println("\n");
//        }
//        catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//    }

    public static String[] detectSentences(String sentence) throws  Exception{
        InputStream input = new FileInputStream("C:/Users/gasso/apache nlp models/en-sent.bin");
        SentenceModel model = new SentenceModel(input);
        SentenceDetectorME detector = new SentenceDetectorME(model);
        String sentences[] = detector.sentDetect(sentence);
        return sentences;
    }

    public static String[] tokenize(String text) throws Exception {
        InputStream input = new FileInputStream("C:/Users/gasso/apache nlp models/en-token.bin");
        TokenizerModel model = new TokenizerModel(input);
        TokenizerME tokenizer = new TokenizerME(model);
        String[] tokens = tokenizer.tokenize(text);
        return tokens;
    }
    public static Span[][] recognizeEntities(String[] words) throws Exception{
        InputStream inputDate = new FileInputStream("C:/Users/gasso/apache nlp model/en-ner-date.bin");
        InputStream inputLoc = new FileInputStream("C:/Users/gasso/apache nlp model/en-ner-location.bin");
        InputStream inputOrg = new FileInputStream("C:/Users/gasso/apache nlp model/en-ner-organisation.bin");
        InputStream inputPer = new FileInputStream("C:/Users/gasso/apache nlp model/en-ner-person.bin");
        InputStream inputMon = new FileInputStream("C:/Users/gasso/apache nlp model/en-ner-money.bin");
        InputStream inputPerc = new FileInputStream("C:/Users/gasso/apache nlp model/en-ner-percentage.bin");
        InputStream inputTime = new FileInputStream("C:/Users/gasso/apache nlp model/en-ner-time.bin");

        TokenNameFinderModel modelDate = new TokenNameFinderModel(inputDate);
        TokenNameFinderModel modelLoc = new TokenNameFinderModel(inputLoc);
        TokenNameFinderModel modelOrg = new TokenNameFinderModel(inputOrg);
        TokenNameFinderModel modelPer = new TokenNameFinderModel(inputPer);
        TokenNameFinderModel modelMon = new TokenNameFinderModel(inputMon);
        TokenNameFinderModel modelPerc = new TokenNameFinderModel(inputPerc);
        TokenNameFinderModel modelTime = new TokenNameFinderModel(inputTime);

        NameFinderME nameFinderDate = new NameFinderME(modelDate);
        NameFinderME nameFinderLoc = new NameFinderME(modelLoc);
        NameFinderME nameFinderOrg = new NameFinderME(modelOrg);
        NameFinderME nameFinderPer = new NameFinderME(modelPer);
        NameFinderME nameFinderMon = new NameFinderME(modelMon);
        NameFinderME nameFinderPerc = new NameFinderME(modelPerc);
        NameFinderME nameFinderTime = new NameFinderME(modelTime);

        Span[][] entities = new Span[7][];
        entities[0] = nameFinderDate.find(words);
        entities[1] = nameFinderDate.find(words);
        entities[2] = nameFinderDate.find(words);
        entities[3] = nameFinderDate.find(words);
        entities[4] = nameFinderDate.find(words);
        entities[5] = nameFinderDate.find(words);
        entities[6] = nameFinderDate.find(words);
        return entities;
    }
    public static String[] posTag(String[] tokens) throws Exception{
        InputStream input = new FileInputStream("C:/Users/gasso/apache nlp models/en-pos-maxent.bin");
        POSModel model = new POSModel(input);
        POSTaggerME tagger = new POSTaggerME(model);
        String[] tags = tagger.tag(tokens);
        return tags;

    }

    public static String[] lemmatize(String[] tokens, String[] posTags) throws Exception{
        InputStream input = new FileInputStream("C:/Users/gasso/apache nlp models/en-lemmatizer.bin");
        LemmatizerModel model = new LemmatizerModel(input);
        LemmatizerME categorizer = new LemmatizerME(model);
        String[] lemmaTokens = categorizer.lemmatize(tokens, posTags);
        return lemmaTokens;
    }

    public static DoccatModel trainCategorizerModel() throws Exception{
        InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(new File("src/main/java/edu/esprit/chatbot/categories.txt"));
        ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, StandardCharsets.UTF_8);
        ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

        DoccatFactory factory = new DoccatFactory(new FeatureGenerator[] { new BagOfWordsFeatureGenerator() });
        TrainingParameters params = ModelUtil.createDefaultTrainingParameters();
        params.put(TrainingParameters.CUTOFF_PARAM,0);

        DoccatModel model = DocumentCategorizerME.train("en", sampleStream, params, factory);
        return model;
    }

    public static String categorize(DoccatModel model, String[] tokens) throws Exception{
        DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);

        double[] probabilityOfOutcomes = myCategorizer.categorize(tokens);
        String category = myCategorizer.getBestCategory(probabilityOfOutcomes);
        System.out.println("Category: " + category);

        return category;
    }

    public static String findCategory(String text) throws Exception{
        String[] sents = detectSentences(text);
        String[] tokens = tokenize(text);
        String[] posTags = posTag(tokens);
        String[] lemmas = lemmatize(tokens, posTags);
        DoccatModel model = trainCategorizerModel();
        String category = categorize(model, tokens);
        return category;
    }


}
