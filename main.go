package main

import (
	"bufio"
	"fmt"
	"io"
	"os"
	"strconv"
)

var iost *Iost

type Iost struct {
	Scanner *bufio.Scanner
	Writer  *bufio.Writer
}

func NewIost(fp io.Reader, wfp io.Writer) *Iost {
	const BufSize = 2000005
	scanner := bufio.NewScanner(fp)
	scanner.Split(bufio.ScanWords)
	scanner.Buffer(make([]byte, BufSize), BufSize)
	return &Iost{Scanner: scanner, Writer: bufio.NewWriter(wfp)}
}
func (i *Iost) Text() string {
	if !i.Scanner.Scan() {
		panic("scan failed")
	}
	return i.Scanner.Text()
}
func (i *Iost) Atoi(s string) int                 { x, _ := strconv.Atoi(s); return x }
func (i *Iost) GetNextInt() int                   { return i.Atoi(i.Text()) }
func (i *Iost) Atoi64(s string) int64             { x, _ := strconv.ParseInt(s, 10, 64); return x }
func (i *Iost) GetNextInt64() int64               { return i.Atoi64(i.Text()) }
func (i *Iost) Atof64(s string) float64           { x, _ := strconv.ParseFloat(s, 64); return x }
func (i *Iost) GetNextFloat64() float64           { return i.Atof64(i.Text()) }
func (i *Iost) Print(x ...interface{})            { fmt.Fprint(i.Writer, x...) }
func (i *Iost) Printf(s string, x ...interface{}) { fmt.Fprintf(i.Writer, s, x...) }
func (i *Iost) Println(x ...interface{})          { fmt.Fprintln(i.Writer, x...) }
func isLocal() bool                               { return os.Getenv("WITHIN") == "TEMPTATION" }
func main() {
	fp := os.Stdin
	wfp := os.Stdout
	if isLocal() {
		fp, _ = os.Open(os.Getenv("REMEMBER_YOU_AS_LONG_AS_I_CAN"))
	}
	iost = NewIost(fp, wfp)
	defer func() {
		iost.Writer.Flush()
	}()
	solve()
}
func solve() {
	N := iost.GetNextInt()
	M := iost.GetNextInt()

	Ni := map[int]int{}
	for i := 1; i < N+1; i++ {
		Ni[i] = i
	}

	for {
		for i := 1; i < N+1; i++ {
			iost.Printf("%d", Ni[i])
			if i != N {
				iost.Printf(" ")
			} else {
				iost.Printf("\n")
			}
		}
		if Ni[1] == (M - N + 1) {
			break
		}
		for i := 1; i < N+1; i++ {
			if Ni[N+1-i] != (M + 1 - i) {
				Ni[N+1-i]++
				for l := 1; l < N+1; l++ {
					if (N + 1 - i + l) > N {
						break
					}
					Ni[N+1-i+l] = (Ni[N+1-i] + l)
				}
				break
			}
		}
	}
}

type Bit []int

func (b Bit) Len() int {
	return len(b)
}
func (b Bit) Add(p int, x interface{}) {
	b[p] += x.(int)
}

func (b Bit) Sum(p []int) interface{} {
	sum := 0
	for _, i := range p {
		sum += b[i]
	}
	return sum
}

type FenwickTreeInterface interface {
	Len() int
	Add(p int, x interface{}) // Add processes a[p] += x.
	Sum(p []int) interface{}  // Sum returns a[p[0]] + a[p[1]] + ... + a[len(p) - 1].
}

// FenwickTree Data structure that can efficiently update elements and calculate prefix sums in a table of numbers.
type FenwickTree struct {
	a FenwickTreeInterface
	p [32]int
}

// NewFenwickTree Constructor
func NewFenwickTree(a FenwickTreeInterface) *FenwickTree {
	return &FenwickTree{a: a}
}

// Add processes a[p] += x.
func (f *FenwickTree) Add(p int, x interface{}) {
	for p++; p <= f.a.Len(); p += p & -p {
		f.a.Add(p-1, x)
	}
}

// Sum returns a[0] + a[1] + ... + a[r - 1].
func (f *FenwickTree) Sum(r int) interface{} {
	p := f.p[:0]
	for ; r > 0; r -= r & -r {
		p = append(p, r-1)
	}
	return f.a.Sum(p)
}
