%%allvars = who;
newAllvars = cell(64,1);
for i= 1:64
    if i <24 
        newAllvars{i} = allvars{i};
    elseif i> 40
        newAllvars{i-17} = allvars{i};
    else
        newAllvars{i+24} = allvars{i};
    end
end

twonewAllvars = cell(32,1);
for i= 1:64
    if i <= 12
        twonewAllvars{i} = newAllvars{i};
    elseif i >= 24 && i<=31
        twonewAllvars{i-11} = newAllvars{i};
        %12 left
    elseif i >= 48 && i<=59
        twonewAllvars{i-27} = newAllvars{i};
    end
end
 
xMatrix = zeros(length(newAllvars), 1000);
newXMatrix = zeros(length(twonewAllvars), 1000);
%%64 is length of allVars
for j=1:64
    name = allvars{j};
    currentMat = eval(name);
    xMatrix(j,:) = lastThousand(currentMat);
end

for j = 1:length(twonewAllvars)
    name = twonewAllvars{j};
    currentMat = eval(name);
    newXMatrix(j,:) = lastThousand(currentMat);
end

YMatrix = zeros(64, 1);
for j=1:64
    if j>47
        %%%Not arrythmia
        YMatrix(j) = -1;
    else
        YMatrix(j) = 1;
    end
end

newYMatrix = zeros(length(twonewAllvars), 1);
for j=1:length(twonewAllvars)
    if j>20
        %%%Not arrythmia
        newYMatrix(j) = -1;
    else
        newYMatrix(j) = 1;
    end
end


SVMModel = fitcsvm(xMatrix,YMatrix,'KernelFunction','rbf','ClassNames',[-1 1]);
newSVMModel = fitcsvm(newXMatrix,newYMatrix,'KernelFunction','rbf','ClassNames',[-1 1]);

