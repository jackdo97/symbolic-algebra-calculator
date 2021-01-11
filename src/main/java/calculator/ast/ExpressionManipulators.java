package calculator.ast;

import calculator.interpreter.Environment;
import calculator.errors.EvaluationError;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
import datastructures.concrete.DoubleLinkedList;
import calculator.gui.ImageDrawer;

/**
 * All of the public static methods in this class are given the exact same parameters for
 * consistency. You can often ignore some of these parameters when implementing your
 * methods.
 *
 * Some of these methods should be recursive. You may want to consider using public-private
 * pairs in some cases.
 */
public class ExpressionManipulators {
    /**
     * Checks to make sure that the given node is an operation AstNode with the expected
     * name and number of children. Throws an EvaluationError otherwise.
     */
    private static void assertNodeMatches(AstNode node, String expectedName, int expectedNumChildren) {
        if (!node.isOperation()
                && !node.getName().equals(expectedName)
                && node.getChildren().size() != expectedNumChildren) {
            throw new EvaluationError("Node is not valid " + expectedName + " node.");
        }
    }

    /**
     * Accepts an 'toDouble(inner)' AstNode and returns a new node containing the simplified version
     * of the 'inner' AstNode.
     *
     * Preconditions:
     *
     * - The 'node' parameter is an operation AstNode with the name 'toDouble'.
     * - The 'node' parameter has exactly one child: the AstNode to convert into a double.
     *
     * Postconditions:
     *
     * - Returns a number AstNode containing the computed double.
     *
     * For example, if this method receives the AstNode corresponding to
     * 'toDouble(3 + 4)', this method should return the AstNode corresponding
     * to '7'.
     * 
     * This method is required to handle the following binary operations
     *      +, -, *, /, ^
     *  (addition, subtraction, multiplication, division, and exponentiation, respectively) 
     * and the following unary operations
     *      negate, sin, cos
     *
     * @throws EvaluationError  if any of the expressions contains an undefined variable.
     * @throws EvaluationError  if any of the expressions uses an unknown operation.
     */
    public static AstNode handleToDouble(Environment env, AstNode node) {
        assertNodeMatches(node, "toDouble", 1);
        AstNode exprToConvert = node.getChildren().get(0);
        return new AstNode(toDoubleHelper(env.getVariables(), exprToConvert));
    }

    private static double toDoubleHelper(IDictionary<String, AstNode> variables, AstNode node) {
        // There are three types of nodes, so we have three cases. 
        if (node.isNumber()) {
            return node.getNumericValue();
        } else if (node.isVariable()) {
            String var = node.getName();
            if (!variables.containsKey(var)) {
                throw new EvaluationError("Undefined variable");
            }
            return toDoubleHelper(variables, variables.get(var));
        } else {
            String operation = node.getName();
            IList<AstNode> children = node.getChildren();

            if (children.size() == 2) {
                double leftHandSide = toDoubleHelper(variables, children.get(0));
                double rightHandSide = toDoubleHelper(variables, children.get(1));
                switch(operation) {
                    case "+":
                        return leftHandSide + rightHandSide;
                    case "-":
                        return leftHandSide - rightHandSide;
                    case "*":
                        return leftHandSide * rightHandSide;
                    case "/":
                        return leftHandSide / rightHandSide;
                    default:
                        return Math.pow(leftHandSide, rightHandSide);
                }
            } else {
                double value = toDoubleHelper(variables, children.get(0));
                switch (operation) {
                    case "negate":
                        return -value;
                    case "sin":
                        return Math.sin(value);
                    case "cos":
                        return Math.cos(value);
                    default:
                        throw new EvaluationError("Unknown operation");
                }
            }
        }
    }

    /**
     * Accepts a 'simplify(inner)' AstNode and returns a new node containing the simplified version
     * of the 'inner' AstNode.
     *
     * Preconditions:
     *
     * - The 'node' parameter is an operation AstNode with the name 'simplify'.
     * - The 'node' parameter has exactly one child: the AstNode to simplify
     *
     * Postconditions:
     *
     * - Returns an AstNode containing the simplified inner parameter.
     *
     * For example, if we received the AstNode corresponding to the expression
     * "simplify(3 + 4)", you would return the AstNode corresponding to the
     * number "7".
     *
     * Note: there are many possible simplifications we could implement here,
     * but you are only required to implement a single one: constant folding.
     *
     * That is, whenever you see expressions of the form "NUM + NUM", or
     * "NUM - NUM", or "NUM * NUM", simplify them.
     */
    public static AstNode handleSimplify(Environment env, AstNode node) {
        // Try writing this one on your own!
        // Hint 1: Your code will likely be structured roughly similarly
        //         to your "handleToDouble" method
        // Hint 2: When you're implementing constant folding, you may want
        //         to call your "handleToDouble" method in some way
        // Hint 3: When implementing your private pair, think carefully about
        //         when you should recurse. Do you recurse after simplifying
        //         the current level? Or before?

        assertNodeMatches(node, "simplify", 1);
        return simplifyHelper(env.getVariables(), node.getChildren().get(0));
    }

    private static AstNode simplifyHelper(IDictionary<String, AstNode> variables, AstNode node) {
        if (node.isNumber()) {
            return node;
        } else if (node.isVariable()) {
            String var = node.getName();
            if (!variables.containsKey(var)) {
                return node;
            } else {
                return simplifyHelper(variables, variables.get(var));
            }
        } else {
            String operation = node.getName();
            IList<AstNode> children = node.getChildren();
            IList<AstNode> newChildren = new DoubleLinkedList<>();

            if (children.size() == 2) {
                newChildren.add(simplifyHelper(variables, children.get(0)));
                newChildren.add(simplifyHelper(variables, children.get(1)));
                AstNode newLeftChild = newChildren.get(0);
                AstNode newRightChild = newChildren.get(1);
                if (newLeftChild.isNumber() && newRightChild.isNumber() &&
                        (operation.equals("+") || operation.equals("-") || operation.equals("*"))) {
                    double val1 = toDoubleHelper(variables, newLeftChild);
                    double val2 = toDoubleHelper(variables, newRightChild);
                    switch(operation) {
                        case "+":
                            return new AstNode(val1 + val2);
                        case "-":
                            return new AstNode(val1 - val2);
                        default:
                            return new AstNode(val1 * val2);
                    }
                }
            } else {
                newChildren.add(simplifyHelper(variables, children.get(0)));
            }

            return new AstNode(node.getName(), newChildren);
        }
    }

    /**
     * Accepts an Environment variable and a 'plot(exprToPlot, var, varMin, varMax, step)'
     * AstNode and generates the corresponding plot on the ImageDrawer attached to the
     * environment. Returns some arbitrary AstNode.
     *
     * Example 1:
     *
     * >>> plot(3 * x, x, 2, 5, 0.5)
     *
     * This method will receive the AstNode corresponding to 'plot(3 * x, x, 2, 5, 0.5)'.
     * Your 'handlePlot' method is then responsible for plotting the equation
     * "3 * x", varying "x" from 2 to 5 in increments of 0.5.
     *
     * In this case, this means you'll be plotting the following points:
     *
     * [(2, 6), (2.5, 7.5), (3, 9), (3.5, 10.5), (4, 12), (4.5, 13.5), (5, 15)]
     *
     * ---
     *
     * Another example: now, we're plotting the quadratic equation "a^2 + 4a + 4"
     * from -10 to 10 in 0.01 increments. In this case, "a" is our "x" variable.
     *
     * >>> c := 4
     * 4
     * >>> step := 0.01
     * 0.01
     * >>> plot(a^2 + c*a + a, a, -10, 10, step)
     *
     * ---
     *
     * @throws EvaluationError  if any of the expressions contains an undefined variable.
     * @throws EvaluationError  if varMin > varMax
     * @throws EvaluationError  if 'var' was already defined
     * @throws EvaluationError  if 'step' is zero or negative
     */
    public static AstNode plot(Environment env, AstNode node) {
        assertNodeMatches(node, "plot", 5);
        IList<AstNode> children = node.getChildren();
        IDictionary<String, AstNode> variables = env.getVariables();

        AstNode var = children.get(1);
        if (variables.containsKey(var.getName())) {
            throw new EvaluationError("var is already defined");
        }

        AstNode varMin = children.get(2);
        AstNode varMax = children.get(3);

        if ((varMin.isVariable() && !variables.containsKey(varMin.getName())) ||
                (varMax.isVariable() && !variables.containsKey(varMax.getName()))) {
            throw new EvaluationError("undefined variable");
        }

        double min = toDoubleHelper(variables, varMin);
        double max = toDoubleHelper(variables, varMax);
        if (min > max) {
            throw new EvaluationError("varMin > varMax");
        }

        AstNode step = children.get(4);
        double increment = toDoubleHelper(variables, step);
        if (increment <= 0.0) {
            throw new EvaluationError("step is 0 or negative");
        }

        if ((step.isVariable() && !variables.containsKey(step.getName()))) {
            throw new EvaluationError("undefined variable");
        }

        IList<Double> xValues = new DoubleLinkedList<>();
        IList<Double> yValues = new DoubleLinkedList<>();
        String varName = var.getName();
        for (double i = 0; i <= max - min; i += increment) {
            double xVal = min + i;
            xValues.add(xVal);
            variables.put(varName, new AstNode(xVal));
            yValues.add(toDoubleHelper(variables, children.get(0)));
            variables.remove(varName);
        }
        ImageDrawer drawer = env.getImageDrawer();
        drawer.drawScatterPlot("", "x-axis", "y-axis", xValues, yValues);
        return new AstNode(1);
    }
}
