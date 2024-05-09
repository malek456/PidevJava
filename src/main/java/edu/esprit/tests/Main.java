package edu.esprit.tests;

import ai.onnxruntime.*;

import java.nio.FloatBuffer;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try (OrtEnvironment environment = OrtEnvironment.getEnvironment();
             OrtSession session = environment.createSession("C:\\Users\\BOUZIDI MALEK\\IdeaProjects\\gestionVol\\src\\main\\resources\\model.onnx", new OrtSession.SessionOptions())) {

            // Prepare the input data as a 2D array
            float[][] inputData = new float[][]{{2024, 12, 25, 5}}; // Notice the double braces
            OnnxTensor inputTensor = OnnxTensor.createTensor(environment, inputData);
            Map<String, OnnxTensor> inputs = Collections.singletonMap(session.getInputNames().iterator().next(), inputTensor);

            // Run the model
            try (OrtSession.Result results = session.run(inputs)) {
                for (Map.Entry<String, OnnxValue> entry : results) {
                    if (entry.getValue() instanceof OnnxSequence) {
                        OnnxSequence sequence = (OnnxSequence) entry.getValue();
                        List<? extends OnnxValue> sequenceElements = sequence.getValue(); // This is the correct way to get elements

                        for (OnnxValue sequenceElement : sequenceElements) {
                            if (sequenceElement instanceof OnnxTensor) {
                                OnnxTensor tensor = (OnnxTensor) sequenceElement;
                                float[] probabilities = (float[]) tensor.getValue();
                                // Handle probabilities array, such as printing or finding top probabilities
                                System.out.println(Arrays.toString(probabilities));
                            }
                        }
                    }
                }
            }
        } catch (OrtException e) {
            e.printStackTrace();
        }
    }
}
