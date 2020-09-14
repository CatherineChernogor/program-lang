let vowel = 'eyuioa';
let conson = 'qwrtpsdfghjklzxcvbnm';
v_count = 0;
c_count = 0;

for (let i = 0; i < word.length; i++) {
    for (let j = 0; j < vowel.length; j++) {
        if (word[i] == vowel[j]) v_count += 1;
    }
}
c_count = word.length - v_count;

console.log('c_count:', c_count, 'v_count:', v_count)

