//lint:file-ignore U1000 using template
package main

import (
	"bufio"
	"fmt"
	"io"
	"math"
	"math/bits"
	"os"
	"sort"
	"strconv"
	"strings"
)

// INF18 は最大値を表す数
const INF18 = int(2e18) + int(2e9)

// INF9 は最大値を表す数
const INF9 = int(2e9)

// N10_6は10^6
const N10_6 = int(1e6)

var in *In
var out *Out

func calc() {
	N := in.NInt()

	// slice
	sl := in.NInts(N)
	out.Println(sl)

	// map
	mp := in.NIntMap(N)
	out.Println(mp)

	// map 2次元
	m2 := in.NIntMap2d(N, N)
	out.Println(m2)

	// 文字列を1文字ずつsliceに格納する
	chararr := in.NCharAsArray()
	out.Println(chararr)
}

func main() {
	// interactiveならfalseにすること。
	in, out = InitIo(true)
	defer out.Flush()

	calc()
}

func debug(args ...interface{}) {
	fmt.Fprintln(os.Stderr, args...)
}

// ==================================================
// 入出力操作
// ==================================================
type In struct {
	// NString は 次の入力を文字列として読み込んで返します。
	NString func() string
}

type Out struct {
	writer io.Writer
	Flush  func()
}

// InitIo は inとoutを初期化します。
func InitIo(buffer bool) (*In, *Out) {
	bufsize := 4 * 1024 * 1024 // 4MB

	// 入力はずっとバッファーでいいらしい。ほんとう？
	// TODO バッファなしfmt.Fscanf(os.Stdin)だとTLEだった。要調査
	_in := bufio.NewScanner(os.Stdin)
	_in.Split(bufio.ScanWords)
	_in.Buffer(make([]byte, bufsize), bufsize)
	in := func() string {
		_in.Scan()
		return _in.Text()
	}

	// 出力はバッファon/offが必要
	var out io.Writer
	var flush func()

	if buffer {
		_out := bufio.NewWriterSize(os.Stdout, bufsize)
		out = _out
		flush = func() {
			_out.Flush()
		}
	} else {
		out = os.Stdout
		flush = func() {}
	}

	return &In{in}, &Out{out, flush}
}

// NextBytes は 次の入力をbyteの配列として読み込んで返します。
// 遅いから極力使わない。
func (in *In) NBytes() []byte {
	return []byte(in.NString())
}

// NextInt は 次の入力を数値として読み込んで返します。
func (in *In) NInt() int {
	i, _ := strconv.Atoi(in.NString())
	return i
}

// NextInt2 は 次の2つの入力を数値として読み込んで返します。
func (in *In) NInt2() (int, int) {
	return in.NInt(), in.NInt()
}

// NextInt2d は 次の2つの入力を数値n1,n2として読み込んで、n1+d1, n2+d2を返します。
func (in *In) NInt2d(d1, d2 int) (int, int) {
	return in.NInt() + d1, in.NInt() + d2
}

// NextInt3 は 次の3つの入力を数値として読み込んで返します。
func (in *In) NInt3() (int, int, int) {
	return in.NInt(), in.NInt(), in.NInt()
}

// NextInt2d は 次の3つの入力を数値n1,n2,n3として読み込んで、n1+d1, n2+d2, n3+d3を返します。
func (in *In) NInt3d(d1, d2, d3 int) (int, int, int) {
	return in.NInt() + d1, in.NInt() + d2, in.NInt() + d3
}

// NextInt4 は 次の4つの入力を数値として読み込んで返します。
func (in *In) NInt4() (int, int, int, int) {
	return in.NInt(), in.NInt(), in.NInt(), in.NInt()
}

// NextInts は 次のn個の入力を数値として読み込んで、配列として返します。
func (in *In) NInts(n int) sort.IntSlice {
	a := make([]int, n)
	for i := 0; i < n; i++ {
		a[i] = in.NInt()
	}
	return sort.IntSlice(a)
}

// NextLongIntAsArray は 次の入力を数値として読み込み、各桁を要素とした配列を返します。
func (in *In) NLongIntAsArray() []int {
	s := in.NString()
	l := len(s)
	arr := make([]int, l)
	for i := 0; i < l; i++ {
		arr[i] = int(s[i] - '0')
	}

	return arr
}

// NextFloat は 次の入力を実数値として読み込み、値を返します。
func (in *In) NFloat() float64 {
	f, _ := strconv.ParseFloat(in.NString(), 64)
	return f
}

// NextFloatAsInt は次の入力を実数rとして読み込み、r * 10^base の値を返します。
func (in *In) NFloatAsInt(base int) int {
	if base%10 == 0 {
		panic("baseは小数点の最大桁数を指定する")
	}

	s := in.NString()
	index := strings.IndexByte(s, '.')

	// 小数点がなければそのまま返す
	if index == -1 {
		n, _ := strconv.Atoi(s)
		return n * pow(10, base)
	}

	// 末尾の0を消しておく
	for s[len(s)-1] == '0' {
		s = s[:len(s)-1]
	}

	// 整数部分 * 10^base + 小数部分 * 10^(足りない分)
	s1, s2 := s[:index], s[index+1:]
	n, _ := strconv.Atoi(s1)
	m, _ := strconv.Atoi(s2)

	return n*pow(10, base) + m*pow(10, base-len(s2))
}

// NextIntMap は N回の入力を数値として読み込み、mapを返します。
func (in *In) NIntMap(N int) map[int]int {
	m := map[int]int{}
	for i := 0; i < N; i++ {
		m[i] = in.NInt()
	}
	return m
}

// NextIntMap2d は N * M 回の入力を数値として読み込み、mapを返します。
func (in *In) NIntMap2d(N int, M int) map[int]map[int]int {
	m := make(map[int]map[int]int)

	for i := 0; i < N; i++ {
		m[i] = make(map[int]int)
		for l := 0; l < M; l++ {
			m[i][l] = in.NInt()
		}
	}
	return m
}

// NextOneStrAsArray は 次の入力を文字列として読み込み、1文字毎に分解し、値を返します。
func (in *In) NCharAsArray() []string {
	s := in.NString()
	return strings.Split(s, "")
}

// Println は引数をスペース区切りで出力し、最後に改行を出力します。
func (out *Out) Println(a ...interface{}) {
	fmt.Fprintln(out.writer, a...)
}

// Printf はformatにしたがってaを整形して出力します。
func (out *Out) Printf(format string, a ...interface{}) {
	fmt.Fprintf(out.writer, format, a...)
}

// PrintStringsln は文字列配列の各要素をスペース区切りで出力し、最後に改行を出力します。
func (out *Out) PrintStringsln(a []string) {
	out.Println(strings.Join(a, " "))
}

// PrintIntsLn は整数配列の各要素をスペース区切りで出力し、最後に改行を出力します。
func (out *Out) PrintIntsLn(a []int) {
	b := make([]string, len(a))
	for i, v := range a {
		b[i] = fmt.Sprint(v)
	}
	out.Println(strings.Join(b, " "))
}

func (out *Out) PrintLenAndIntsLn(a []int) {
	b := make([]string, len(a)+1)
	b[0] = fmt.Sprint(len(a))
	for i, v := range a {
		b[i+1] = fmt.Sprint(v)
	}
	out.Println(strings.Join(b, " "))
}

// Putcは一文字出力します。
func (out *Out) Putc(c byte) {
	out.Printf("%c", c)
}

// YesNo は condが真ならYes, 偽ならNoを出力します。
func (out *Out) YesNo(cond bool) {
	if cond {
		out.Println("Yes")
	} else {
		out.Println("No")
	}
}

func (out *Out) YESNO(cond bool) {
	if cond {
		out.Println("YES")
	} else {
		out.Println("NO")
	}
}

// ==================================================
// 数値操作
// ==================================================

// max は aとbのうち大きい方を返します。
func max(a, b int) int {
	if a > b {
		return a
	}
	return b
}

// min は aとbのうち小さい方を返します。
func min(a, b int) int {
	if a < b {
		return a
	}
	return b
}

// chmax は aとbのうち大きい方をaに設定します。
func chmax(a *int, b int) {
	*a = max(*a, b)
}

// chmin は aとbのうち小さい方をaに設定します。
func chmin(a *int, b int) {
	*a = min(*a, b)
}

// abs は aの絶対値を返します。
func abs(a int) int {
	if a > 0 {
		return a
	}
	return -a
}

// pow は aのb乗を返します。
func pow(a, b int) int {
	ans := 1
	for b > 0 {
		if b%2 == 1 {
			ans *= a
		}
		a, b = a*a, b/2
	}
	return ans
}

// divceil は a/b の結果を正の無限大に近づけるように丸めて返します。
func divceil(a, b int) int {
	if a%b == 0 || a/b < 0 {
		return a / b
	}
	return (a + b - 1) / b
}

// divfloor は a/b の結果を負の無限大に近づけるように丸めて返します。
func divfloor(a, b int) int {
	if a%b == 0 || a/b > 0 {
		return a / b
	}
	if b < 0 {
		a, b = -a, -b
	}
	return (a - b + 1) / b
}

// powmod は (x^n) mod m を返します。
func powmod(x, n, m int) int {
	if n == 0 {
		return 1
	}

	x = x % m
	if x == 0 {
		return 0
	}

	ans := 1
	for n > 0 {
		if n%2 == 1 {
			ans = (ans * x) % m
		}
		x = (x * x) % m
		n /= 2
	}
	return ans
}

// chiはcondがtrueのときok, falseのときngを返します。
func chi(cond bool, ok, ng int) int {
	if cond {
		return ok
	}
	return ng
}

// chbはcondがtrueのときok, falseのときngを返します。
func chb(cond bool, ok, ng byte) byte {
	if cond {
		return ok
	}
	return ng
}

// chsはcondがtrueのときok, falseのときngを返します。
func chs(cond bool, ok, ng string) string {
	if cond {
		return ok
	}
	return ng
}

// extmulはa*bの結果を返します。
// 2つ目の値が+1ならオーバーフロー、-1ならアンダーフローが発生したことを表します。
func extmul(a, b int) (int, int) {
	if a < 0 {
		a, b = -a, -b
	}
	if a == 0 || b == 0 {
		return 0, 0
	} else if a > 0 && b > 0 && a > math.MaxInt64/b {
		return 0, +1
	} else if a > math.MinInt64/b {
		return 0, -1
	}
	return a * b, 0
}

// ==================================================
// ビット操作
// ==================================================

// nthbit はaのn番目のビットを返します。
func nthbit(a int, n int) int { return int((a >> uint(n)) & 1) }

// popcount はaのうち立っているビットを数えて返します。
func popcount(a int) int {
	return bits.OnesCount(uint(a))
}

func xor(a, b bool) bool { return a != b }

// ==================================================
// 文字列操作
// ==================================================

// toLowerCase は sをすべて小文字にした文字列を返します。
func toLowerCase(s string) string {
	return strings.ToLower(s)
}

// toUpperCase は sをすべて大文字にした文字列を返します。
func toUpperCase(s string) string {
	return strings.ToUpper(s)
}

// isLower はbが小文字かどうかを判定します
func isLower(b byte) bool {
	return 'a' <= b && b <= 'z'
}

// isUpper はbが大文字かどうかを判定します
func isUpper(b byte) bool {
	return 'A' <= b && b <= 'Z'
}

// ==================================================
// 配列
// ==================================================
// NewIntInt は数値の二次元配列を作成します。
func NewIntInt(rows, cols, val int) [][]int {
	a := make([][]int, rows)
	for i := 0; i < rows; i++ {
		a[i] = make([]int, cols)

		for j := 0; j < cols; j++ {
			a[i][j] = val
		}
	}

	return a
}

// compressはnumbersで渡した値を座標圧縮します。
func compress(numbers map[int]int) (map[int]int, []int) {
	keys := sort.IntSlice{}
	for i := range numbers {
		keys = append(keys, i)
	}
	sort.Sort(keys)
	for i, v := range keys {
		numbers[v] = i
	}

	return numbers, keys
}

// ==================================================
// 図形
// ==================================================
// 3点の座標から角度を算出します。(180度以下の値で返す。)
func angle(A []int, B []int, C []int) float64 {
	ab := []int{}
	ab = append(ab, B[0]-A[0])
	ab = append(ab, B[1]-A[1])
	ac := []int{}
	ac = append(ac, C[0]-A[0])
	ac = append(ac, C[1]-A[1])

	abac := ab[0]*ac[0] + ab[1]*ac[1]
	abn := (ab[0] * ab[0]) + (ab[1] * ab[1])
	acn := (ac[0] * ac[0]) + (ac[1] * ac[1])
	radian := math.Acos(float64(abac) / (math.Sqrt(float64(abn) * float64(acn))))
	angle := radian * 180 / math.Pi

	return angle
}
