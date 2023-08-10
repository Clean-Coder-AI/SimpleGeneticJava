# Randomness In Evolution
This is a simple simulation to see how introduction of randomness in evolution affects the fitness of the population.

Here are the assumptions made:

1. Population has two genes hot gene and cold gene
2. For hot climate hot gene is more fit
3. For cold climate cold gene is more fit
4. Top 20% of population contributes 60% of the next generation
5. For remaining 80%, based on if randomness is added or not each crossover produces one child
6. Population is kept fixed from generation to generation in the first version

My hypothesis and it is kind of already intuitive is that randomness helps maintain the diversity of the population.
That is the nature's way of keeping the population fit for the change in environment.

## Results

It is noted that randomness plays part until few generations. The more generations that passes with either HOT or COLD climate
randomness becomes irrelevant because most of the populations would have been fit for the given climate.

If climate is to change before the population becomes at this peak fitness introduction of randomness helps population bounce back in changing climate

Here is the plot for 30 generations and 20 number of genes [plot.png](plot.png)
