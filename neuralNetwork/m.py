import numpy as np
import tensorflow as tf
import matplotlib
matplotlib.use('TkAgg')
import matplotlib.pyplot as plt


inputFile = open("0321_totalPoints_data.txt")
dim = [4193,6]
inpRaw = []
c = 0
for line in inputFile:
	inpRaw.append(float(line.strip()))



def constructArray(rawInput, numDimensions, dimensionsArray):
	if(numDimensions==1):
		return rawInput
	ret = []
	for i in range(0,dimensionsArray[0]):
		lengthA = int(len(rawInput)/dimensionsArray[0]);
		ret.append(constructArray(rawInput[i*lengthA:(i+1)*lengthA],numDimensions-1,dimensionsArray[1:]))
	return ret;

def weight_variable(shape):
  initial = tf.truncated_normal(shape, stddev=0.1)
  return tf.Variable(initial)

inp = constructArray(inpRaw,len(dim),dim)

targetsFile = open("0321_totalPoints_results.txt")
dim = [4193,2]
outRaw = []
c = 0
for line in targetsFile:
	outRaw.append(float(line.strip()))

targets = constructArray(outRaw,len(dim),dim)

i = 0
while i < len(targets):
	if targets[i][0] == 0 or targets[i][1] == 0:
		targets = np.concatenate((targets[:i],targets[(i+1):]))
		inp = np.concatenate((inp[:i],inp[(i+1):]))
	i+=1

within = 50
wInp = []
wTarg = []
nInp = []
nTarg = []
for i in range(len(targets)):
	diff = abs(targets[i][1]-targets[i][0])
	if(diff<within):
		wInp.append(inp[i])
		wTarg.append(targets[i])
	else:
		nInp.append(inp[i])
		nTarg.append(targets[i])

sess = tf.InteractiveSession()
IndivHid = 10
CombHid = 20
insizenum=6

MINI_BATCH_SIZE = 10
EPOCHS = 60
PRINTA = 10
LEARNING_RATE = 5e-4

inputs = tf.placeholder(dtype = tf.float32, shape = [None,insizenum])
targetValues = tf.placeholder(dtype=tf.float32, shape = [None,2])
in_size = tf.size(inputs)
half = tf.cast(insizenum/2,dtype=tf.int32)
red_inputs = inputs[:,0:half]
blue_inputs = inputs[:,half:]

out1 = tf.matmul(red_inputs,[[1/3],[1/3],[1/3]])
out2 = tf.matmul(blue_inputs,[[1/3],[1/3],[1/3]])
out = tf.divide(tf.concat([out1,out2],1),tf.constant(3, dtype=tf.float32))

correct_prediction = tf.equal(tf.argmax(targetValues,1), tf.argmax(out,1))
accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))
targetsA = targetValues[:,:1]
targetsB = targetValues[:,1:]
omtA = abs(out1-targetsA)
omtB = abs(out2-targetsB)
pea = omtA/(targetsA)
peb = omtB/(targetsB)
totalOutputCount = tf.cast((tf.shape(omtA)[0]*2),dtype=tf.float32)
accuracy2 = 1-(tf.reduce_sum(pea)+tf.reduce_sum(peb))/totalOutputCount


sess.run(tf.global_variables_initializer())

print("close matches: ",len(wInp))

a = accuracy.eval(feed_dict={inputs: wInp, targetValues: wTarg})
a2 = accuracy2.eval(feed_dict={inputs: wInp, targetValues: wTarg})
print("WL%: ",a," Acc (1-PE)%: ",a2)


print("clear wins: ",len(nInp))

a = accuracy.eval(feed_dict={inputs: nInp, targetValues: nTarg})
a2 = accuracy2.eval(feed_dict={inputs: nInp, targetValues: nTarg})
print("WL%: ",a," Acc (1-PE)%: ",a2)

a2 = accuracy2.eval(feed_dict={inputs: inp, targetValues: targets})
print(a2)
print(out1.eval(feed_dict={inputs: inp, targetValues: targets}))
print(targetsA)
print(out2.eval(feed_dict={inputs: inp, targetValues: targets}))
print(targetsB)
