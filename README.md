![Finished program](/imgs/calculator-screenshot.png)

The user can interactively enter commands in the bottom pane. These commands can do different things, ranging from just evaluating expressions to graphing actual output. The lines that start with >>> are lines where the user typed in input; the following lines are the output of that operation.

## Example 1: Basic usage and variables

<pre class=" line-numbers language-pseudocode"><code class=" language-pseudocode"><span class="token operator">&gt;&gt;&gt;</span> <span class="token number">3</span> <span class="token operator">+</span> <span class="token number">2</span> <span class="token operator">*</span> <span class="token number">7</span>
<span class="token number">17</span>

<span class="token operator">&gt;&gt;&gt;</span> x <span class="token operator">+</span> y
x <span class="token operator">+</span> y

<span class="token operator">&gt;&gt;&gt;</span> x <span class="token operator">:</span><span class="token operator">=</span> <span class="token number">1</span>
<span class="token number">1</span>

<span class="token operator">&gt;&gt;&gt;</span> x <span class="token operator">+</span> y
<span class="token number">1</span> <span class="token operator">+</span> y

<span class="token operator">&gt;&gt;&gt;</span> y <span class="token operator">:</span><span class="token operator">=</span> <span class="token number">2</span>
<span class="token number">2</span>

<span class="token operator">&gt;&gt;&gt;</span> x <span class="token operator">+</span> y
<span class="token number">3</span><span aria-hidden="true" class="line-numbers-rows"><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span></span></code></pre>

## Example 2: Variable redefinition

<pre class=" line-numbers language-pseudocode"><code class=" language-pseudocode"><span class="token operator">&gt;&gt;&gt;</span> y <span class="token operator">:</span><span class="token operator">=</span> x <span class="token operator">+</span> <span class="token number">3</span>
x <span class="token operator">+</span> <span class="token number">3</span>

<span class="token operator">&gt;&gt;&gt;</span> y
x <span class="token operator">+</span> <span class="token number">3</span>

<span class="token operator">&gt;&gt;&gt;</span> x <span class="token operator">:</span><span class="token operator">=</span> <span class="token number">4</span>
<span class="token number">4</span>

<span class="token operator">&gt;&gt;&gt;</span> y
<span class="token number">7</span>

<span class="token operator">&gt;&gt;&gt;</span> x <span class="token operator">:</span><span class="token operator">=</span> <span class="token number">8</span>
<span class="token number">8</span>

<span class="token operator">&gt;&gt;&gt;</span> y
<span class="token number">11</span>
<span aria-hidden="true" class="line-numbers-rows"><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span></span></code></pre>

## Example 3: Symbolic evaluation

<pre class=" line-numbers language-pseudocode"><code class=" language-pseudocode"><span class="token operator">&gt;&gt;&gt;</span> y <span class="token operator">:</span><span class="token operator">=</span> x <span class="token operator">+</span> <span class="token number">2</span> <span class="token operator">+</span> <span class="token number">3</span> <span class="token operator">*</span> <span class="token function">sin</span><span class="token punctuation">(</span>x<span class="token punctuation">)</span> <span class="token operator">+</span> <span class="token punctuation">(</span><span class="token number">20</span> <span class="token operator">+</span> <span class="token number">30</span><span class="token punctuation">)</span><span class="token operator">/</span><span class="token number">70</span>
x <span class="token operator">+</span> <span class="token number">2</span> <span class="token operator">+</span> <span class="token number">3</span> <span class="token operator">*</span> <span class="token function">sin</span><span class="token punctuation">(</span>x<span class="token punctuation">)</span> <span class="token operator">+</span> <span class="token number">50</span> <span class="token operator">/</span> <span class="token number">70</span>

<span class="token operator">&gt;&gt;&gt;</span> y
x <span class="token operator">+</span> <span class="token number">2</span> <span class="token operator">+</span> <span class="token number">3</span> <span class="token operator">*</span> <span class="token function">sin</span><span class="token punctuation">(</span>x<span class="token punctuation">)</span> <span class="token operator">+</span> <span class="token number">50</span> <span class="token operator">/</span> <span class="token number">70</span>

<span class="token operator">&gt;&gt;&gt;</span> x <span class="token operator">:</span><span class="token operator">=</span> <span class="token number">4</span>
<span class="token number">4</span>

<span class="token operator">&gt;&gt;&gt;</span> y
<span class="token number">6</span> <span class="token operator">+</span> <span class="token number">3</span> <span class="token operator">*</span> <span class="token function">sin</span><span class="token punctuation">(</span><span class="token number">4</span><span class="token punctuation">)</span> <span class="token operator">+</span> <span class="token number">50</span> <span class="token operator">/</span> <span class="token number">70</span>

<span class="token operator">&gt;&gt;&gt;</span> <span class="token function">toDouble</span><span class="token punctuation">(</span>y<span class="token punctuation">)</span>
<span class="token number">4.44387822836193</span>
<span aria-hidden="true" class="line-numbers-rows"><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span></span></code></pre>

