%Takes a vector w and returns the last thousand vector v
function v = lastThousand(w)
lengthofW = length(w);
v= zeros(1, 1000);
for i = 1:1000
    v(i) = w(lengthofW-1000+i);
end



