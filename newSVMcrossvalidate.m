twoPrevec = cell(32,1);
for i= 1:64
    if i > 12 && i< 24
        twoPrevec{i-12} = newAllvars{i};
    elseif i >31 && i<48
        twoPrevec{i-20} = newAllvars{i};
        %12 left
    elseif i >59
        twoPrevec{i-32} = newAllvars{i};
    end
end

twoCrossX = zeros(length(twoPrevec), 1000);
for j=1:32
    name = allvars{j};
    currentMat = eval(name);
    twoCrossX(j,:) = lastThousand(currentMat);
end

twoCrossY = zeros(length(twonewAllvars), 1);
for j=1:length(twonewAllvars)
    if j>28
        %%%Not arrythmia
        twoCrossY(j) = -1;
    else
        twoCrossY(j) = 1;
    end
end

[label,score] = predict(newSVMModel,twoCrossX);
